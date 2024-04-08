import colors from "@/assets/color.js";
import React, { useEffect, useState } from "react";
import { StyleSheet, Text, View, ScrollView, Button } from "react-native";
import MenuBarCompo from "@/components/MenuBarCompo";
import BigdataMainHeader from "@/templates/bigdata/BigdataMainHeader";
import BigdataMainBody from "@/templates/bigdata/BigdataMainBody";
import { useNavigation } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import * as Location from "expo-location";
import { getDistrict } from "@/api/bigdata";

const BigDataMain = () => {
  // (임시) 로그아웃 버튼
  const toLogOut = async () => {
    console.log("로그아웃!!!!!");

    try {
      await AsyncStorage.removeItem("token");
      console.log("token 삭제함");
      return true;
    } catch (exception) {
      console.log("token 삭제실패 ㅜㅠ");
      return false;
    }
  };

  // 현재 내 위치 조회하는 API
  const [currentGu, setCurrentGu] = useState("별나라");
  const [lat, setLat] = useState(1);
  const [lon, setLon] = useState(1);

  useEffect(() => {
    const getLocation = async () => {
      try {
        await Location.requestForegroundPermissionsAsync();
        const {
          coords: { latitude, longitude },
        } = await Location.getCurrentPositionAsync({ accuracy: 5 });

        // const location = await Location.reverseGeocodeAsync(
        //   { latitude, longitude },
        //   { useGoogleMaps: false },
        // );

        // BigdataMainBody로 넘기기 위한 lat, lon 구하기
        setLat(latitude);
        setLon(longitude);

        // 현재 구 이름 구하는 axios 요청
        const data = await getDistrict(latitude, longitude);
        const currentGu = data.data.gu;
        setCurrentGu(currentGu);
      } catch (error) {
        console.log("BigDataMain.tsx getLocation 에러", error);
      }
    };
    getLocation();
  }, []);

  return (
    <View style={styles.container}>
      <Button title="로그아웃" onPress={toLogOut} />

      <View style={styles.menubarWrapper}>
        <MenuBarCompo title={"두드림길 정보"} />
      </View>

      <ScrollView style={styles.scrollView} centerContent={true}>
        <BigdataMainHeader currentGu={currentGu} />
        <BigdataMainBody lat={lat} lon={lon} />
      </ScrollView>
    </View>
  );
};

export default BigDataMain;

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
  text: {
    fontSize: 50,
  },
});
