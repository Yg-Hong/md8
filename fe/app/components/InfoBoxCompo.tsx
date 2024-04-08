import React from "react";
import { SafeAreaView, StyleSheet, TextInput, Text, View } from "react-native";
import colors from "@/assets/color";
import Title from "@/atoms/Title";

import CalorieSvg from "@/assets/icons/Calorie.svg";
import DistanceSvg from "@/assets/icons/Distance.svg";
import TimeSvg from "@/assets/icons/Time.svg";
import WaterLightSvg from "@/assets/icons/WaterLight.svg";
import StoreLightSvg from "@/assets/icons/StoreLight.svg";
import SafeLightSvg from "@/assets/icons/SafeLight.svg";
import ToiletLightSvg from "@/assets/icons/ToiletLight.svg";
import RecommendSvg from "@/assets/icons/Recommend.svg";
import NoRecommendSvg from "@/assets/icons/NoRecommend.svg";

export default function InfoBoxCompo({
  mode,
  calorie,
  distance,
  time,
  water,
  safe,
  store,
  toilet,
  recommend,
}) {
  switch (mode) {
    // 산책정보
    case "WALK_INFO":
      return (
        <View>
          <View style={styles.titleContainer}>
            <Title content={"ㅓㅓ"}></Title>
          </View>
          <View style={styles.container}>
            <View style={styles.routeContainer}>
              <View style={styles.smallRoute}>
                <DistanceSvg width={27.816} height={27.816} />
                <Text style={styles.text}>{distance}km</Text>
              </View>

              <View style={styles.smallRoute}>
                <CalorieSvg width={27.816} height={27.816} />
                <Text style={styles.text}>{calorie}kcal</Text>
              </View>

              <View style={styles.smallRoute}>
                <TimeSvg width={27.816} height={27.816} />
                <Text style={styles.text}>{time ? time : "time 입력"}</Text>
              </View>
            </View>
          </View>
        </View>
      );
    //   편의시설 정보
    case "AMENITY_INFO":
      return (
        <View>
          <View style={styles.titleContainer}>
            <Title content={"wpahr"}></Title>
          </View>
          <View style={styles.container}>
            <View style={styles.amenityRouteContainer}>
              <Text
                style={{
                  fontFamily: "font4",
                  color: colors.darkbrown,
                  marginBottom: 10,
                }}
              >
                산책 경로에 이런 편의시설이 있어요
              </Text>

              <View style={styles.smallRoute}>
                <WaterLightSvg width={27.816} height={27.816} />
                <Text style={styles.amenityText}>
                  {water ? `음수대 ${water}곳` : "음수대 없음"}
                </Text>
              </View>

              <View style={styles.smallRoute}>
                <SafeLightSvg width={27.816} height={27.816} />
                <Text style={styles.amenityText}>
                  {safe ? `안전지킴이 시설 ${safe}곳` : "안전지킴이 없음"}
                </Text>
              </View>

              <View style={styles.smallRoute}>
                <StoreLightSvg width={27.816} height={27.816} />
                <Text style={styles.amenityText}>
                  {store ? `편의점 ${store}곳` : "편의점 없음"}
                </Text>
              </View>

              <View style={styles.smallRoute}>
                <ToiletLightSvg width={27.816} height={27.816} />
                <Text style={styles.amenityText}>
                  {toilet ? `화장실 ${toilet}곳` : "화장실 없음"}
                </Text>
              </View>
            </View>
          </View>
        </View>
      );
    //추천 공개 비공개 여부
    case "RECOMMEND_INFO":
      return (
        <View>
          <View style={styles.titleContainer}>
            <Title content={"ㄹㅇㄴㄹ"}></Title>
          </View>
          <View style={styles.container}>
            <View style={styles.routeContainer}>
              <View style={styles.smallRoute}>
                {/* recommend 값이 true라면 추천 공개, false라면 추천 비공개 */}
                {recommend ? (
                  <>
                    <RecommendSvg width={27.816} height={27.816} />
                    <Text style={styles.amenityText}>추천 공개 중</Text>
                  </>
                ) : (
                  <>
                    <NoRecommendSvg width={27.816} height={27.816} />
                    <Text style={styles.amenityText}>추천 비공개 중</Text>
                  </>
                )}
              </View>
            </View>
          </View>
        </View>
      );
    default:
      return <></>;
  }
}

const styles = StyleSheet.create({
  container: {
    // width: "100%",
    // 요소의 가로 영역 값을 최대로 설정
    alignSelf: "stretch",
    paddingVertical: 20,
    paddingHorizontal: 10,
    gap: 5,
    alignItems: "center",
    justifyContent: "center",
    borderWidth: 1,
    borderColor: colors.beige,
    borderRadius: 8,
    backgroundColor: colors.lightbeige,
  },
  titleContainer: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "flex-start",
    marginBottom: 3,
  },
  routeContainer: {
    width: "100%",
    paddingRight: 5,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  smallRoute: {
    flexDirection: "row",
    alignItems: "center",
    gap: 4,
    color: colors.darkbrown,
    fontFamily: "font4",
    marginTop: 2,
    marginBottom: 2,
  },
  amenityRouteContainer: {
    width: "100%",
    paddingRight: 5,
    justifyContent: "space-between",
    alignItems: "flex-start",
  },
  text: {
    color: colors.darkbrown,
    fontFamily: "font5",
    fontSize: 16,
    paddingLeft: 10,
  },
  amenityText: {
    paddingLeft: 7,
    fontFamily: "font4",
    color: colors.darkbrown,
  },
});
