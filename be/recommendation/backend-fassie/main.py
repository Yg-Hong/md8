import psycopg2
import asyncio
import numpy as np
import faiss
import socket

from aiokafka import AIOKafkaConsumer
from fastapi import FastAPI
import py_eureka_client.eureka_client as eureka_client

d = 4
reco_model = faiss.IndexFlatL2(d)
app = FastAPI()
tracking_list = []

loop = asyncio.get_event_loop()
KAFKA_INSTANCE = "j10a208.p.ssafy.io:9092"
consumer = AIOKafkaConsumer("tracking-reco",
                            bootstrap_servers=KAFKA_INSTANCE,
                            loop=loop,
                            group_id="tracking-reco",
                            auto_offset_reset="latest")

async def consume():
    await consumer.start()
    try:
        async for msg in consumer:
            msg_result = msg.value.decode('utf-8').split(',')
            print(msg_result)

            float_list = [float(item) for item in msg_result]
            tracking_list.append(float_list)

            index_to_exclude = 0
            new_list = [item for i, item in enumerate(float_list) if i != index_to_exclude]
            reco_model.add(np.array([new_list]).astype('float32'))
    finally:
        await consumer.stop()

@app.on_event("startup")
async def db_to_recomodel():
    db = psycopg2.connect(host='j10a208.p.ssafy.io', dbname='ms8',user='ms8',password='ms8!@',port=5432)
    cursor = db.cursor()
    cursor.execute(f"select tracking_id, latitude, longitude, distance, time from md.tracking where is_recommend = true and is_valid_to_recommend = true;")
    result = cursor.fetchall()
    for row in result:
        tracking_list.append([row[0], row[1], row[2], row[3], row[4]])
    modified_list = [[row[col] for col in range(len(row)) if col != 0] for row in tracking_list]
    reco_model.add(np.array(modified_list).astype('float32'))

@app.on_event("startup")
async def startup_event():
    loop.create_task(consume())

@app.on_event("startup")
async def startup_event():
    await eureka_client.init_async(eureka_server="http://j10a208.p.ssafy.io:8761/eureka/",
                                   app_name="faiss",
                                   instance_port=8000,
                                   instance_host="localhost"
                                   )

@app.get("/api/faiss")
def recommendation(lon: float, lat: float, time: int, dist: int,k: int):
    print(lat, lon, dist, time, k)

    D, I = reco_model.search(np.array([[lat, lon, dist, time]]), k)
    result = []
    for i, indices in enumerate(I):
        for idx in indices:
            result.append(tracking_list[idx][0])
    return result
