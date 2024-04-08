import React, { useEffect, useState } from "react";
import {
  Modal,
  View,
  Text,
  ScrollView,
  Button,
  StyleSheet,
} from "react-native";
import colors from "@/assets/color";
import InputBoxCompo from "@/components/InputBoxCompo";
import TrackingInfoCompo from "@/components/tracking/TrackingInfoCompo";
import FacilityInfoCompo from "@/components/tracking/FacilityInfoCompo";
import ToggleCompo from "@/components/ToggleCompo";
import ButtonCompo from "@/components/ButtonCompo";
import axios from "axios";
import LiteMapCompo from "@/components/tracking/LiteMapCompo";
import { useNavigation } from "@react-navigation/native";
import { get } from "react-native/Libraries/TurboModule/TurboModuleRegistry";
import AsyncStorage from "@react-native-async-storage/async-storage";
import MenuBarCompo from "@/components/MenuBarCompo";

interface LatLng {
  latitude: number;
  longitude: number;
}

interface TrackingInputPageProps {
  trackingId?: number;
  alertModalVisiable: boolean;
  setAlertModalVisiable: (value: boolean) => void;
  myRoute: Array<LatLng>;
  startPosition: LatLng;
  time: number;
  distance: number;
  kcal: number;
  drinking: Set<number>;
  safezone: Set<number>;
  toilet: Set<number>;

  title?: string;
  content?: string;
  isReco?: boolean;
}

const TrackingInputPage = (props: TrackingInputPageProps) => {
  const [userId, setUserId] = useState<number>(0);

  // 유저 정보 가져오기
  useEffect(() => {
    AsyncStorage.getItem("userId").then((value) => {
      if (value) {
        setUserId(JSON.parse(value));
      }
    });
  }, []);

  const [title, setTitle] = useState<string>(props.title ? props.title : "");
  const [content, setContent] = useState<string>(
    props.content ? props.content : "",
  );
  const [isReco, setIsReco] = useState<boolean>(
    props.isReco === undefined || props.isReco === null ? true : props.isReco,
  );

  const onCancelPress = () => {
    console.log("Modal has been closed.");
    props.setAlertModalVisiable(false);
  };

  const onPressSave = () => {
    if (props.trackingId) {
      console.log("수정하기");
      editMyRoute(userId);
    } else {
      saveMyRoute(userId);
    }
  };

  const navigation = useNavigation();

  // 산책로 저장하기 axios 호출 =======================================
  const saveMyRoute = (userId: number) => {
    const param = {
      title: title,
      content: content,
      latitude: props.startPosition.latitude,
      longitude: props.startPosition.longitude,
      latitudeDelta: 0.002,
      longitudeDelta: 0.002,
      route: props.myRoute,
      time: props.time,
      kcal: props.kcal,
      distance: props.distance,
      isRecommend: isReco,
      amenitiesList: [...props.drinking, ...props.safezone, ...props.toilet],
    };

    console.log(param);

    axios
      .post(
        `http://j10a208a.p.ssafy.io:8082/api/v1/tracking?userId=${userId}`,
        param,
      )
      .then((res) => {
        console.log(res.data);
        console.log(res.data.data.trackingId);
        props.setAlertModalVisiable(false);
        navigation.navigate("TrackingInfoDetail", {
          trackingId: res.data.data.trackingId,
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 산책로 수정하기 axios 호출 =======================================
  const editMyRoute = (userId: number) => {
    const param = {
      title: title,
      content: content,
      isRecommend: isReco,
    };

    console.log(param);

    axios
      .patch(
        `http://j10a208a.p.ssafy.io:8082/api/v1/tracking/${props.trackingId}?userId=${userId}`,
        param,
      )
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <Modal
      animationType="slide"
      visible={props.alertModalVisiable}
      onRequestClose={() => {
        console.log("Modal has been closed.");
        props.setAlertModalVisiable(false);
      }}
    >
      {/* 배경 부분 */}
      <View
        onTouchEnd={() => {
          props.setAlertModalVisiable(false);
        }}
        // style={styles.backgroundViewContainer}
      >
        {/* 모달 부분 */}
        <MenuBarCompo title="산책 경로 입력" />
        <ScrollView
          onTouchEnd={(e) => {
            e.stopPropagation();
          }}
        >
          <View style={styles.root}>
            <View style={styles.rootContainer}>
              {/* 맵 부분 */}
              <LiteMapCompo
                initialRegion={{
                  latitude: props.startPosition.latitude,
                  longitude: props.startPosition.longitude,
                  latitudeDelta: 0.002,
                  longitudeDelta: 0.002,
                }}
                myRoute={props.myRoute}
                startPosition={props.startPosition}
              />
            </View>

            <InputBoxCompo
              mode="SMALL_INPUT"
              title="제목"
              placeholder="제목을 입력하세요"
              onSubmitEditing={() => {}}
              reset={false}
              content={title}
              setContent={setTitle}
            />

            <InputBoxCompo
              mode="LARGE_INPUT"
              title="내용"
              placeholder="산책을 더 잘 기억하도록 느낌과 감정을 남겨보세요"
              onSubmitEditing={() => {}}
              reset={false}
              content={content}
              setContent={setContent}
            />

            <TrackingInfoCompo
              distance={props.distance ? props.distance : 0}
              time={props.time ? props.time : 0}
              kcal={props.kcal ? props.kcal : 0}
            />

            <FacilityInfoCompo
              drinking={props.drinking.size ? props.drinking.size : 0}
              safezone={props.safezone.size ? props.safezone.size : 0}
              toilet={props.toilet.size ? props.toilet.size : 0}
            />

            <View style={styles.recoContainer}>
              <View style={styles.titleContainer}>
                <Text style={styles.titleText}>
                  {`이 경로를 다른 사람들에게 추천하시겠어요?`}
                </Text>
              </View>
              <View style={styles.toggleContainer}>
                <ToggleCompo
                  trueText={"추천하기"}
                  falseText={"추천하지 않기"}
                  isToggle={isReco}
                  setIsToggle={setIsReco}
                  fontcolor={isReco ? colors.green : colors.darkbrown}
                />
              </View>
            </View>

            <ButtonCompo
              mode="TWO_BUTTONS"
              textWhite={"취소"}
              textGreen={props.trackingId ? "수정" : "등록"}
              onPressWhite={onCancelPress}
              onPressGreen={onPressSave}
            />
          </View>
        </ScrollView>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  root: {
    width: "100%",
    paddingHorizontal: 22,
    gap: 10,
    paddingBottom: 100,
  },
  rootContainer: {
    width: "100%",
    paddingTop: 10,
    paddingBottom: 10,
    flexDirection: "column",
    alignItems: "center",
    rowGap: 3,
    columnGap: 3,
    alignSelf: "stretch",
  },
  modalViewContainer: {
    width: "100%",
    paddingTop: 10,
    paddingBottom: 10,
    flexDirection: "column",
    alignItems: "center",
    rowGap: 3,
    columnGap: 3,
    alignSelf: "stretch",
  },
  recoContainer: {
    width: "100%",
    paddingLeft: 5,
    paddingRight: 5,
    flexDirection: "column",
    alignItems: "center",
    rowGap: 5,
    columnGap: 5,
  },
  titleText: {
    color: colors.darkbrown,
    fontFamily: "font6",
    fontSize: 16,
  },
  titleContainer: {
    flexDirection: "row",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
  toggleContainer: {
    flexDirection: "row",
    paddingTop: 5,
    paddingBottom: 5,
    justifyContent: "flex-end",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
});

export default TrackingInputPage;
