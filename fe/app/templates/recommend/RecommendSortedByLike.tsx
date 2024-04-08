import colors from "@/assets/color.js";
import React from "react";
import { useEffect, useState } from "react";
import { StyleSheet, Text, View, FlatList } from "react-native";
import { getRecommendation } from "@/api/recommend";
import TrackCompo from "@/components/TrackCompo";
import * as Location from "expo-location";
import { useNavigation } from "@react-navigation/native";

const RecommendSortedByLike = ({ time, dist }) => {
  const navigation = useNavigation();

  // 현재 사용자 위치 기반 위경도 구하기
  const tempTime = 30;
  const tempDist = 100;

  // 추천 산책로 조회 API
  const [data, setData] = useState([]);

  useEffect(() => {
    // 현재 사용자 위치 기반 위경도 구하기
    const getLocation = async () => {
      try {
        await Location.requestForegroundPermissionsAsync();
        const {
          coords: { latitude, longitude },
        } = await Location.getCurrentPositionAsync({ accuracy: 5 });

        // console.log("1. latitude, longitude 구함", latitude, longitude);
        // console.log("2. ", tempTime, tempDist);

        getRecommendation(longitude, latitude, time, dist).then((response) => {
          // bookMark 오름차순으로 정렬
          const tempData = response.data.recoResult;
          tempData.sort((a, b) => b.bookMark - a.bookMark);
          setData(tempData);
        });
      } catch (error) {
        console.log("RecommendSortedByLike.tsx getLocation 에러", error);
      }
    };
    getLocation();
  }, []);

  const renderItem = ({ item }) => (
    <View style={styles.renderedItems}>
      <TrackCompo
        result={item}
        onPress={() => {
          navigation.navigate("TrackingInfoDetail", { trackingId: item.id });
        }}
      />
    </View>
  );

  return (
    <FlatList
      style={{ width: "100%" }}
      data={data}
      renderItem={renderItem}
      keyExtractor={(item) => item.trackingId}
    />
  );
};

export default RecommendSortedByLike;

const styles = StyleSheet.create({
  renderedItems: {
    width: "100%",
    paddingVertical: 8,
  },
});
