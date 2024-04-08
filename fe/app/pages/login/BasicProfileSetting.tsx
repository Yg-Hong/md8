import colors from "@/assets/color.js";
import React, { useEffect, useState } from "react";
import {
  Keyboard,
  StyleSheet,
  Text,
  View,
  ImageBackground,
} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import LoginInputCompo from "@/components/login/LoginInputCompo";
import LoginDropDownCompo from "@/components/login/LoginDropDownCompo";
import ButtonCompo from "@/components/ButtonCompo";
import { useNavigation } from "@react-navigation/native";

// axios import
import { getUserInfo, updateUserInfo } from "@/api/user";

// recoil import
import { useRecoilValue } from "recoil";
import { UserIdAtom, TokenAtom } from "@/recoil/UserAtom";
import { TouchableWithoutFeedback } from "react-native-gesture-handler";

const BasicProfileSetting = () => {
  const navigation = useNavigation();

  // 현재 로그인한 유저의 userId를 로컬에서 가져와 사용자 정보를 업데이트 하기
  const [userId, setUserId] = useState(0);
  const [nickName, setNickName] = useState("");
  const [time, setTime] = useState("");
  const [dist, setDist] = useState("");
  const [profile, setProfile] = useState("");

  useEffect(() => {
    const getFirstInfo = async () => {
      try {
        // AsyncStorage에서 현재 userId 가져오기
        // 가져온 userId가 string이므로 int로 변환하기
        const tempUserId = await AsyncStorage.getItem("userId");
        const currentUser = parseInt(tempUserId);
        setUserId(currentUser);
        console.log("현재 userId: ", currentUser);
        console.log(typeof currentUser);

        // userId로 유저 정보 조회하는 API
        await getUserInfo(currentUser).then((response) => {
          // console.log(response.data);
          setNickName(response.data.nickName);
          const tempTime = response.data.time;
          const tempDist = response.data.distance;
          setTime(tempTime.toString());
          setDist(tempDist.toString());
          setProfile(response.data.profile);
        });
      } catch (error) {
        console.log("처음 프로필 설정에서 getUserId 에러", error);
      }
    };
    getFirstInfo();
  }, []);

  // 버튼을 누르면 회원 정보 수정 요청 보내기
  const handlePressGreen = () => {
    updateUserInfo(userId, nickName, 1, time, dist);
    navigation.navigate("AfterTabNavigator");
  };

  return (
    <TouchableWithoutFeedback
      onPress={() => {
        Keyboard.dismiss();
      }}
    >
      <View style={styles.container}>
        {/* 프로필 이미지 */}
        <View style={styles.imageContainer}>
          <ImageBackground
            source={{ uri: profile }}
            style={styles.profileImg}
            imageStyle={{ borderRadius: 158 }}
          />
        </View>

        {/* 닉네임 */}
        <LoginInputCompo
          defaultValue={nickName}
          handleInputChange={setNickName}
          placeHolder={"닉네임을 정해주세요"}
          description={"님"}
        />

        {/* 선호시간 */}
        <LoginInputCompo
          defaultValue={time}
          handleInputChange={setTime}
          placeHolder={"산책 시간을 정해주세요"}
          description={"분"}
        />

        {/* 선호거리 */}
        <LoginInputCompo
          defaultValue={dist}
          handleInputChange={setDist}
          placeHolder={"산책 거리를 정해주세요"}
          description={"m"}
        />

        <View style={styles.buttonContainer}>
          <ButtonCompo
            mode={"ONE_BUTTON"}
            textGreen={"등록하기"}
            onPressGreen={handlePressGreen}
          />
        </View>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default BasicProfileSetting;

const styles = StyleSheet.create({
  container: {
    width: "100%",
    height: "100%",
    paddingHorizontal: 22,
    gap: 20,
    alignItems: "center",
    backgroundColor: "white",
  },
  imageContainer: {
    width: "100%",
    height: "25%",
    justifyContent: "center",
    alignItems: "center",
    marginTop: 40,
    marginBottom: 20,
  },
  buttonContainer: {
    width: "100%",
    marginTop: 40,
  },
  profileImg: {
    width: 100,
    height: 100,
    gap: 20,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 158,
  },
});
