import React, { useEffect, useState } from "react";
import { View, Text, StyleSheet, Image, TouchableOpacity } from "react-native";
import colors from "@/assets/color.js";
import axios from "axios";
import ProfileCompo from "@/components/ProfileCompo";
import MenuBarCompo from "@/components/MenuBarCompo";
import ButtonCompo from "@/components/ButtonCompo";
import ToggleCompo from "@/components/ToggleCompo";
import { useNavigation } from "@react-navigation/native";
import AsyncStorage from "@react-native-async-storage/async-storage";

interface User {
  id: number;
  email: string;
  ageCode: {
    codeId: number;
    name: string;
    detailId: number;
    detailName: string;
    description: string;
  };
  gender: any; // 아직 데이터가 뭔지 자세히 모름..
  nickName: string;
  level: number;
  totalDistance: number;
  profile: string;
  time: number;
  distance: number;
  followerCount: number;
  followingCount: number;
}

const MySettingPage = () => {
  const [user, setUser] = useState({} as User);
  const [isToggleOn, setIsToggleOn] = useState(false);
  const getUser = async () => {
    const userId = await AsyncStorage.getItem("userId");
    await axios
      .get(`http://j10a208a.p.ssafy.io:8080/api/users/${userId}`)
      .then((res) => {
        console.log(res.data.data);
        setUser(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    getUser();
  }, []);

  const navigation = useNavigation();
  const onPressBlockedUser = () => {
    navigation.navigate("BlockedUser");
  };

  const onPressLogout = () => {
    console.log("로그아웃 버튼 클릭 이벤트 구현");
  };

  const onPressWithdrawal = () => {
    console.log("회원 탈퇴 버튼 클릭 이벤트 구현");
  };

  return (
    <View style={styles.rootContainer}>
      <MenuBarCompo title="내 정보 수정" settingButton={false} />

      <ProfileCompo
        userId={user.id}
        image={user.profile}
        nickname={user.nickName}
        rank={user.level}
        association="myself"
      />

      <View style={styles.userSettingContainer}>
        <View style={styles.userPreferContainer}>
          <View style={styles.userPreferRow}>
            <View style={styles.userPrefer}>
              <Text style={styles.userPreferText}>{`선호 소요시간`}</Text>
            </View>
            <View style={styles.userPrefer}>
              <Text style={styles.userPreferText}>
                {user.time ? user.time : 0}
              </Text>
            </View>
          </View>
          <View style={styles.userPreferRow}>
            <View style={styles.userPrefer}>
              <Text style={styles.userPreferText}>{`선호 거리`}</Text>
            </View>
            <View style={styles.userPrefer}>
              <Text style={styles.userPreferText}>
                {user.distance ? user.distance : 0}
              </Text>
            </View>
          </View>

          <View style={styles.userInfoEditButton}>
            <View style={styles.userInfoEditButton2}>
              <ButtonCompo
                mode="ONE_BUTTON"
                textGreen={"유저 정보 수정"}
                onPressGreen={() =>
                  console.log("유저 정보 수정 버튼 클릭 이벤트 구현")
                }
                textWhite={""}
                onPressWhite={() => {}}
              />
            </View>
          </View>
        </View>
        <View>
          <View style={styles.buttonContainer}>
            <View style={styles.buttonRow}>
              <TouchableOpacity
                style={styles.buttonTitle}
                onPress={() => {
                  setIsToggleOn(!isToggleOn);
                }}
              >
                <Text style={styles.buttonText}>{`알림 설정`}</Text>
              </TouchableOpacity>
              <View>
                <ToggleCompo
                  isToggle={isToggleOn}
                  setIsToggle={setIsToggleOn}
                  trueText={"켬"}
                  falseText={"끔"}
                />
              </View>
            </View>
            <TouchableOpacity
              style={styles.buttonRow}
              onPress={onPressBlockedUser}
            >
              <View style={styles.buttonTitle}>
                <Text style={styles.buttonText}>{`차단 계정`}</Text>
              </View>
              <View></View>
            </TouchableOpacity>
            <TouchableOpacity style={styles.buttonRow} onPress={onPressLogout}>
              <View style={styles.buttonTitle}>
                <Text style={styles.buttonText}>{`로그 아웃`}</Text>
              </View>
              <View></View>
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.buttonRow}
              onPress={onPressWithdrawal}
            >
              <View style={styles.buttonTitle}>
                <Text style={styles.buttonTextRed}>{`회원 탈퇴`}</Text>
              </View>
              <View></View>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    flexDirection: "column",
    alignItems: "center",
    rowGap: 3,
    columnGap: 3,
  },
  profileContainer: {
    flexDirection: "column",
    alignItems: "center",
  },
  userSettingContainer: {
    width: "100%",
    paddingBottom: 28,
    flexDirection: "column",
    alignItems: "center",
    rowGap: 18,
    columnGap: 18,
  },
  userPreferContainer: {
    paddingTop: 8,
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 8,
    flexDirection: "column",
    alignItems: "flex-start",
    rowGap: 6,
    columnGap: 6,
    alignSelf: "stretch",
    borderBottomColor: colors.darkbrown,
    borderBottomWidth: 1,
  },
  userPreferRow: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  userPrefer: {
    flexDirection: "row",
    alignItems: "center",
    paddingLeft: 30,
    width: 150,
    rowGap: 10,
    columnGap: 10,
  },
  userPreferText: {
    color: colors.darkbrown,
    fontFamily: "font3",
    fontSize: 16,
  },
  userInfoEditButton: {
    paddingTop: 14,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    width: "100%",
  },
  userInfoEditButton2: {
    width: "90%",
  },
  buttonContainer: {
    paddingTop: 14,
    paddingLeft: 6,
    paddingRight: 6,
    paddingBottom: 14,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "flex-start",
    alignSelf: "stretch",
  },
  buttonRow: {
    width: "90%",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    alignSelf: "stretch",
  },
  buttonTitle: {
    flexDirection: "row",
    paddingTop: 10,
    paddingBottom: 10,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  buttonText: {
    color: colors.darkbrown,
    fontFamily: "font5",
    fontSize: 16,
  },
  buttonText2: {
    color: colors.darkbrown,
    fontFamily: "font5",
    fontSize: 16,
    letterSpacing: 1.6,
  },
  buttonTextRed: {
    color: "red",
    fontFamily: "font5",
    fontSize: 16,
  },
});

export default MySettingPage;
