import colors from "@/assets/color.js";
import React, { useState, useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";
import { getAllDudurims } from "@/api/bigdata";
import BigdataCard from "@/components/bigdata/BigdataCard";
import { useNavigation } from "@react-navigation/native";

const BigdataMainBody = ({ lat, lon }) => {
  const navigation = useNavigation();

  // 두드림길 전체 조회 API
  const [dudurimList, setDudurimList] = useState([]);
  useEffect(() => {
    getAllDudurims(lat, lon).then((response) => {
      setDudurimList(response.data.dudurimList);
    });
  }, []);

  // const dudurimList = [
  //   {
  //     id: 8,
  //     title: "선정릉 나들길",
  //     address: "서울 강남구",
  //     level: "보통",
  //     distance: 5.9,
  //     time: "2시간",
  //     water: false,
  //     toilet: true,
  //     store: true,
  //     congestion: 3,
  //     weather: "모래",
  //     fineDust: "1",
  //     degree: "7.86",
  //     image: "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/선정릉 나들길",
  //     icon: "50d",
  //   },
  //   {
  //     id: 95,
  //     title: "양재천 산책길",
  //     address: "서울 서초구",
  //     level: "매우쉬움",
  //     distance: 4,
  //     time: "60분",
  //     water: true,
  //     toilet: true,
  //     store: true,
  //     congestion: 1,
  //     weather: "실 비",
  //     fineDust: "1",
  //     degree: "8.28",
  //     image: "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/양재천 산책길",
  //     icon: "10d",
  //   },
  //   {
  //     id: 6,
  //     title: "구룡산 나들길",
  //     address: "서울 강남구",
  //     level: "보통",
  //     distance: 6.02,
  //     time: "2시간30분",
  //     water: true,
  //     toilet: true,
  //     store: true,
  //     congestion: 1,
  //     weather: "실 비",
  //     fineDust: "1",
  //     degree: "8.08",
  //     image: "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/구룡산 나들길",
  //     icon: "10d",
  //   },
  // ];

  return (
    <View style={styles.container}>
      {dudurimList.map((data, idx) => (
        <BigdataCard
          key={idx}
          data={data}
          dudurimInfoMode={"WHITE"}
          onPress={() => {
            navigation.navigate("BigDataDetail", { trackingId: data.id });
            // console.log("Pressed!");
          }}
        />
      ))}
    </View>
  );
};

export default BigdataMainBody;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: "100%",
    height: "100%",
    paddingHorizontal: 22,
    paddingTop: 25,
    gap: 25,
    paddingBottom: 20,
  },
});
