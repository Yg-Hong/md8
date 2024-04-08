import colors from "@/assets/color.js";
import React, { useEffect, useState } from "react";
import { StyleSheet, Text, View, ScrollView } from "react-native";
import MenuBarCompo from "@/components/MenuBarCompo";
import BigdataDetailHeader from "@/templates/bigdata/BigdataDetailHeader";
import BigdataDetailBody from "@/templates/bigdata/BigdataDetailBody";
import { getOneDudurim } from "@/api/bigdata";

// dudurim 인터페이스 선언
interface Dudurim {
  address: string;
  congestion: number;
  content: string;
  convience: boolean;
  degree: string;
  distance: number;
  drink: boolean;
  fineDust: string;
  icon: string;
  id: number;
  image: string;
  keywords: string;
  level: string;
  time: string;
  title: string;
  toilet: boolean;
  weather: string;
}

const BigdataDetail = ({ route }) => {
  // navigation으로 보낸 파라미터 받아오기
  const trackingId = route.params.trackingId;

  // 두드림길 상세 조회 API
  const [dudurim, setDudurim] = useState<Dudurim>({});
  useEffect(() => {
    getOneDudurim(trackingId).then((response) => {
      setDudurim(response.data);
      console.log("두드림길 단일 조회");
      console.log("키워드 1: ", response.data.keyword);
    });
  }, []);

  // 데이터가 존재할 때만 화면에 렌더링
  if (!dudurim.title) {
    return null; // 또는 로딩 화면 등을 출력할 수 있음
  }

  // const data = {
  //   id: 95,
  //   title: "양재천 산책길",
  //   address: "서울 서초구",
  //   level: "매우쉬움",
  //   distance: 4,
  //   time: "60분",
  //   drink: true,
  //   toilet: true,
  //   convience: true,
  //   congestion: 1,
  //   weather: "실 비",
  //   fineDust: "1",
  //   degree: "8.28",
  //   content:
  //     "다양한 종류의 수목이 우거진 양재시민의 숲을 지나 양재천을 따라 산책길이 이어진다. 여러 종의 어류가 서식하고 있는 생태하천의 여러 생물들을 찾아보는 재미가 있다. 자전거길도 함께 조성되어 있어 자전거를 타보며 달려보는 재미도 있다.",
  //   keywords:
  //     "[[0, 양재천], [1, 산책길], [2, 벚꽃], [3, 라멘], [4, 다리], [5, 서초구], [6, 명곡], [7, 서울], [8, 집], [9, 개포동], [10, 우시], [11, 길], [12, 하천], [13, 곳곳], [14, 봄]]",
  //   image: "https://wp-ht-s3.s3.ap-northeast-2.amazonaws.com/양재천 산책길",
  //   icon: "10d",
  // };

  return (
    <View style={styles.container}>
      <View style={styles.menubarWrapper}>
        <MenuBarCompo />
      </View>
      <ScrollView style={styles.scrollView}>
        <BigdataDetailHeader data={dudurim} />
        <BigdataDetailBody
          title={dudurim.title}
          content={dudurim.content}
          keywords={dudurim.keywords}
        />
      </ScrollView>
    </View>
  );
};

export default BigdataDetail;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "flex-start",
    alignItems: "center",
    backgroundColor: "#ffffff",
  },
  menubarWrapper: {
    width: "100%",
  },
  scrollView: {
    flex: 1,
    width: "100%",
    height: "100%",
  },
});
