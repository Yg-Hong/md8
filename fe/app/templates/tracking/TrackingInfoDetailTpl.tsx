import React, { useEffect, useState } from "react";
import { View, Text, StyleSheet, ScrollView } from "react-native";

import colors from "@/assets/color";
import TrackingInputPage from "@/pages/tracking/TrackingInputPage";
import LiteMapCompo from "@/components/tracking/LiteMapCompo";
import SmallButtonCompo from "@/components/SmallButtonCompo";
import TrackingInfoCompo from "@/components/tracking/TrackingInfoCompo";
import FacilityInfoCompo from "@/components/tracking/FacilityInfoCompo";
import RecoCompo from "@/components/tracking/RecoCompo";
import ButtonCompo from "@/components/ButtonCompo";
import AlertModalCompo from "@/components/AlertModalCompo";
import MenuBarCompo from "@/components/MenuBarCompo";

interface LatLng {
  latitude: number;
  longitude: number;
}

interface TrackingInfoDetailProps {
  trackingId: number;
  title: string;
  createdAt: string;
  content: string;
  initialRegion: {
    latitude: number;
    longitude: number;
    latitudeDelta: number;
    longitudeDelta: number;
  };
  startPosition: LatLng;
  myRoute: Array<LatLng>;
  time: number;
  distance: number;
  kcal: number;
  drinking: number;
  safezone: number;
  toilet: number;
  isReco: boolean;

  onPressDelete: () => void;
  onPrsseLetsTracking: () => void;
}

const TrackingInfoDetailTpl = (props: TrackingInfoDetailProps) => {
  const [inputFormModalVisible, setInputFormModalVisible] =
    useState<boolean>(false);
  const [deleteModalVisible, setDeleteModalVisible] = useState<boolean>(false);
  const [date, setDate] = useState<string>("");

  // 2024-03-29T00:00:00.000 형태로 오는 createdAt을 2024-03-29 형태로 변환
  useEffect(() => {
    setDate(props.createdAt.split("T")[0]);
  }, [props.createdAt]);

  return (
    <View>
      <View style={styles.beigeBackground}>
        <MenuBarCompo title={"산책 경로 상세"} />
      </View>
      <ScrollView>
        {inputFormModalVisible ? (
          <TrackingInputPage
            trackingId={props.trackingId}
            alertModalVisiable={inputFormModalVisible}
            setAlertModalVisiable={setInputFormModalVisible}
            myRoute={props.myRoute}
            startPosition={props.startPosition}
            time={props.time}
            distance={props.distance}
            kcal={props.kcal}
            title={props.title}
            content={props.content}
            isReco={props.isReco}
            drinking={props.drinking}
            safezone={props.safezone}
            toilet={props.toilet}
          />
        ) : (
          <></>
        )}

        {deleteModalVisible ? (
          <AlertModalCompo
            alertModalVisiable={deleteModalVisible}
            setAlertModalVisiable={setDeleteModalVisible}
            title="산책 기록을 삭제하시겠어요?"
            textWhite="취소하기"
            textGreen="삭제하기"
            onPressCancel={() => setDeleteModalVisible(false)}
            onPressConfirm={() => props.onPressDelete()}
          />
        ) : (
          <></>
        )}

        <View style={styles.rootWithBeigeBackground}>
          <View style={styles.alignCenterContainer}>
            <View style={styles.upperContainer}>
              <View style={styles.titleContainer}>
                <Text style={styles.titleText}>{props.title}</Text>
              </View>
              <View style={styles.createAtContainer}>
                <Text style={styles.createAtText}>{date}</Text>
              </View>
            </View>

            {/* 지도 컴포넌트 */}
            <LiteMapCompo
              initialRegion={props.initialRegion}
              startPosition={props.startPosition}
              myRoute={props.myRoute}
            />

            <View style={styles.contentContainer}>
              <Text style={styles.contentText}>{props.content}</Text>
            </View>
            <View style={styles.buttonContainer}>
              <View style={styles.buttonComponent}>
                <SmallButtonCompo
                  mode="BROWN_BUTTON"
                  text="수정"
                  onPress={() => {
                    console.log("수정 버튼 클릭");
                    setInputFormModalVisible(true);
                  }}
                />
              </View>
              <View style={styles.buttonComponent} testID="833:1722">
                <SmallButtonCompo
                  mode="BROWN_BUTTON"
                  text="삭제"
                  onPress={() => {
                    console.log("삭제 버튼 클릭");
                    setDeleteModalVisible(true);
                  }}
                />
              </View>
            </View>
          </View>
        </View>

        <View style={styles.alignCenterContainer2}>
          <TrackingInfoCompo
            distance={props.distance}
            kcal={props.kcal}
            time={props.time}
          />

          <FacilityInfoCompo
            drinking={props.drinking}
            safezone={props.safezone}
            toilet={props.toilet}
          />

          <RecoCompo isReco={props.isReco} />

          <ButtonCompo
            mode="ONE_BUTTON"
            textGreen={"산책하기"}
            onPressGreen={props.onPrsseLetsTracking}
            textWhite={""}
            onPressWhite={() => {}}
          />
        </View>
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  beigeBackground: {
    backgroundColor: colors.lightbeige,
  },
  rootWithBeigeBackground: {
    width: "100%",
    paddingBottom: 10,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 19,
    columnGap: 19,
    backgroundColor: colors.lightbeige,
  },
  alignCenterContainer: {
    width: "100%",
    padding: 10,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 19,
    columnGap: 19,
  },
  alignCenterContainer2: {
    width: "100%",
    paddingHorizontal: 22,
    paddingTop: 30,
    paddingBottom: 100,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 19,
    columnGap: 19,
    gap: 30,
  },
  upperContainer: {
    width: "100%",
    paddingTop: 10,
    paddingBottom: 10,
    flexDirection: "column",
    alignItems: "center",
    rowGap: 5,
    columnGap: 5,
  },
  titleContainer: {
    width: "100%",
    flexDirection: "row",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
  titleText: {
    color: colors.darkbrown,
    fontFamily: "font5",
    fontSize: 21,
  },
  createAtContainer: {
    flexDirection: "row",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
  createAtText: {
    color: colors.darkbrown,
    fontFamily: "font3",
    fontSize: 14,
  },
  contentContainer: {
    flexDirection: "row",
    width: "100%",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  contentText: {
    width: "90%",
    flexShrink: 0,
    color: colors.darkbrown,
    fontFamily: "font4",
    fontSize: 16,
  },
  buttonContainer: {
    flexDirection: "row",
    paddingTop: 6,
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 6,
    justifyContent: "flex-end",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
  buttonComponent: {
    flexDirection: "row",
    justifyContent: "flex-end",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
});

export default TrackingInfoDetailTpl;
