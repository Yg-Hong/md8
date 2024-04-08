import colors from "@/assets/color.js";
import React, { useEffect, useState } from "react";
import { useRef, useMemo, useCallback } from "react";
import { StyleSheet, Text, View, ScrollView } from "react-native";
import BottomSheet from "@/components/BottomSheet";
import MenuBarCompo from "@/components/MenuBarCompo";
import RecommendMainHeader from "@/templates/recommend/RecommendMainHeader";
import RecommendMainBody from "@/templates/recommend/RecommendMainBody";
import {
  BottomSheetBackdrop,
  BottomSheetView,
  BottomSheetModal,
  BottomSheetModalProvider,
} from "@gorhom/bottom-sheet";
import { getUserInfo } from "@/api/user";
import AsyncStorage from "@react-native-async-storage/async-storage";

const RecommendMain = () => {
  // 현재 로그인한 유저의 userId를 로컬에서 가져와 사용자 정보 받아오는 API
  // const [userInfo, setUserInfo] = useState({});
  const [nickName, setNickName] = useState("");
  const [time, setTime] = useState(1);
  const [dist, setDist] = useState(1);

  useEffect(() => {
    const getHeaderInfo = async () => {
      try {
        // AsyncStorage에서 userId를 가져오기 위해 await 키워드로 비동기 처리
        const userId = await AsyncStorage.getItem("userId");
        // console.log("userId 구함: ", userId);
        await getUserInfo(userId).then((response) => {
          // console.log("유저 정보");
          // console.log(response.data);
          setNickName(response.data.nickName);
          setTime(response.data.time);
          setDist(response.data.distance);
        });
      } catch (error) {
        console.log("getUserId 에러", error);
      }
    };
    getHeaderInfo();
  }, []);

  // BottomSheet 띄우는 부분 ================================
  // 1. variables
  const bottomSheetModalRef = useRef<BottomSheetModal>(null);

  // 2. variables
  const snapPoints = useMemo(() => ["25%", "40%"], []);

  // 3. callbacks
  const handlePresentModalPress = useCallback(() => {
    bottomSheetModalRef.current?.present();
    console.log("modal clikced");
  }, []);

  const handlSheetChanges = useCallback((index: number) => {
    console.log("handleSheetChanges", index);
  }, []);

  // 4. backdrop
  const renderBackdrop = useCallback(
    (props) => <BottomSheetBackdrop {...props} pressBehavior={"close"} />,
    [],
  );
  // =======================================================

  return (
    <BottomSheetModalProvider>
      <View style={styles.container}>
        <View style={styles.menubarWrapper}>
          <MenuBarCompo title={"추천 산책"}></MenuBarCompo>
        </View>

        <BottomSheetModal
          ref={bottomSheetModalRef}
          index={1}
          snapPoints={snapPoints}
          onChange={handlSheetChanges}
          backdropComponent={renderBackdrop}
        >
          <BottomSheetView style={styles.modalContentContainer}>
            <BottomSheet
              yesButton={() => {
                console.log("적용하기 버튼");
              }}
              noButton={() => {
                console.log("취소 버튼");
              }}
            ></BottomSheet>
          </BottomSheetView>
        </BottomSheetModal>

        <RecommendMainHeader
          nickName={nickName}
          time={time}
          distance={dist}
        ></RecommendMainHeader>

        <RecommendMainBody
          handlePresentModalPress={handlePresentModalPress}
          time={time}
          dist={dist}
        ></RecommendMainBody>
      </View>
    </BottomSheetModalProvider>
  );
};

export default RecommendMain;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "flex-start",
    alignItems: "center",
    backgroundColor: "#ffffff",
  },
  menubarWrapper: {
    width: "100%",
    position: "absolute",
    top: 0,
    zIndex: 1,
  },
  modalContainer: {
    flex: 1,
    padding: 24,
    backgroundColor: colors.lightgreen,
    alignItems: "center",
    justifyContent: "center",
  },
  modalContentContainer: {
    flex: 1,
    alignItems: "center",
    paddingTop: 20,
    paddingHorizontal: 20,
  },
});
