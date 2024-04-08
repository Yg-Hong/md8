import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View, Image, ImageBackground } from "react-native";
import RecoBackground from "@images/RecoBackground.png";
import SmallButtonCompo from "@/components/SmallButtonCompo";
import { useNavigation } from "@react-navigation/native";

const RecommendMainHeader = ({ nickName, time, distance }) => {
  const navigation = useNavigation();
  const toEditUserInfo = () => {
    console.log("유저 정보 수정하러 가기");
    navigation.navigate("BasicProfileSetting");
  };

  return (
    <View style={styles.container}>
      <ImageBackground
        source={RecoBackground}
        resizeMode="cover"
        style={styles.imageWrapper}
      >
        <View style={styles.textWrapper}>
          <Text style={styles.text}>{nickName} 님의</Text>
          <Text style={styles.text}>선호 소요시간은 {time}분</Text>
          <Text style={styles.text}>선호 거리는 {distance}m</Text>
          <Text style={styles.text}>이예요</Text>
          <SmallButtonCompo
            mode={"BEIGE_BUTTON"}
            text={"수정하기"}
            onPress={toEditUserInfo}
          />
        </View>
      </ImageBackground>
    </View>
  );
};

export default RecommendMainHeader;

const styles = StyleSheet.create({
  container: {
    width: "100%",
    height: 363,
    minHeight: 300,
    justifyContent: "flex-start",
    alignItems: "center",
  },

  imageWrapper: {
    flex: 1,
    width: "100%",
    justifyContent: "center",
    alignItems: "center",
  },
  textWrapper: {
    width: "100%",
    justifyContent: "flex-start",
    paddingTop: 20,
    paddingLeft: 25,
    gap: 15,
  },
  text: {
    fontFamily: "font3",
    fontSize: 20,
    color: colors.darkbrown,
  },
  carousel: {
    flex: 1,
    width: "100%",
    height: 100,
    backgroundColor: colors.lightbeige,
  },
});
