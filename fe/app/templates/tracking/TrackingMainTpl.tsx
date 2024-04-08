import React from "react";
import { View, StyleSheet, Text } from "react-native";

import PinMap from "@/components/tracking/PinMapCompo";
import Map from "@/components/tracking/MapCompo";
import StatusBarCompo from "@/components/tracking/StatusBarCompo";
import StartButton from "@/components/tracking/StartButtonCompo";
import ProgressBar from "@/components/tracking/ProgressBarCompo";
import StartWithGuideButton from "@/components/tracking/StartWithGuideButtonCompo";

import LogoWithLongWidth from "@/assets/images/LogoWithLongWidth";

import colors from "@/assets/color.js";
import AlertModalCompo from "@/components/AlertModalCompo";
import TrackingInputPage from "@/pages/tracking/TrackingInputPage";

interface Region {
  latitude: number;
  longitude: number;
  latitudeDelta?: number;
  longitudeDelta?: number;
}

interface LatLng {
  latitude: number;
  longitude: number;
}

interface Pin {
  lat: number;
  lng: number;
  pinId: number;
  photoURL: string;
}

interface WeatherProps {
  weatherIcon: string;
  description: string;
  temp: number;
}

interface TrackingMainTplProps {
  // Modal props
  completeModalVisiable: boolean;
  setCompleteModalVisiable: (value: boolean) => void;
  clearData: () => void;
  routeFormModalVisiable: boolean;
  setRouteFormModalVisiable: (value: boolean) => void;
  myRoute: Array<LatLng>;
  startPosition: LatLng;
  toiletRegion: Array<LatLng>;
  drinkingRegion: Array<LatLng>;
  safezoneRegion: Array<LatLng>;
  adjacentDrinking: Set<number>;
  adjacentToilet: Set<number>;
  adjacentSafezone: Set<number>;

  // Map props
  region: {
    myLocation: Region;
    initialRegion: Region;
    toiletRegion: Array<Region>;
    drinkingRegion: Array<Region>;
    safezoneRegion: Array<Region>;
    pinRegion: Array<Pin>;
    myRoute: Array<LatLng>;
    guideRoute: Array<LatLng>;
    setGuideRoute: (props: Array<LatLng>) => void;
  };

  // StatusBar props
  userName: string;
  statusBarProps: {
    trackingStatus: number;
    location: string;
    weather: WeatherProps;
    dust: number;
  };

  // TrackingStatus props
  trackingStatusProps: {
    trackingStatus: number;
    setTrackingStatus: (props: number) => void;
  };

  // Progress props
  progressProps: {
    distance: number;
    time: number;
    kcal: number;
    trackingStatus: number;
    setTime: (props: number) => void;
    setTrackingStatus: (props: number) => void;
  };
}

const TrackingMainTpl = ({ props }: { props: TrackingMainTplProps }) => {
  return (
    <View style={styles.rootContainer}>
      {/* 모달 */}
      {props.completeModalVisiable ? (
        <AlertModalCompo
          alertModalVisiable={props.completeModalVisiable}
          setAlertModalVisiable={props.setCompleteModalVisiable}
          title={"산책 기록을 저장하시겠어요?"}
          textWhite={"삭제하기"}
          textGreen={"저장하기"}
          onPressCancel={() => {
            props.setCompleteModalVisiable(false);
            props.clearData();
          }}
          onPressConfirm={() => {
            props.setCompleteModalVisiable(false);
            props.setRouteFormModalVisiable(true);
          }}
        />
      ) : (
        <></>
      )}
      {props.routeFormModalVisiable ? (
        <TrackingInputPage
          alertModalVisiable={props.routeFormModalVisiable}
          setAlertModalVisiable={props.setRouteFormModalVisiable}
          myRoute={props.myRoute}
          startPosition={props.startPosition}
          time={props.progressProps.time}
          distance={props.progressProps.distance}
          kcal={props.progressProps.kcal}
          drinking={props.adjacentDrinking}
          safezone={props.adjacentSafezone}
          toilet={props.adjacentToilet}
        />
      ) : (
        <></>
      )}
      {props.trackingStatusProps.trackingStatus === 0 ||
      props.trackingStatusProps.trackingStatus === 3 ? (
        <PinMap {...props.region} />
      ) : (
        <Map {...props.region} />
      )}

      {/* 상단 로고 & user name 고정 바 */}
      <View style={styles.upperContainer}>
        <LogoWithLongWidth width={95} height={37} />
        {/* <Image /> */}
        <View style={styles.greetingContainer}>
          <View style={styles.greetingContainer}>
            <Text style={styles.greetingText}>{`안녕하세요 `}</Text>
          </View>
          <View style={styles.nameContainer}>
            <View style={styles.nameContainer}>
              <Text style={styles.greetingText}>{`${props.userName}`}</Text>
            </View>
            <View style={styles.nameContainer}>
              <Text style={styles.greetingText}>{`님`}</Text>
            </View>
          </View>
        </View>
      </View>

      {/* 상단 상태 바 : 날씨 & 위치 & 날짜 & 상태 */}
      {props.trackingStatusProps.trackingStatus === 0 ||
      props.trackingStatusProps.trackingStatus === 2 ? (
        <StatusBarCompo {...props.statusBarProps} />
      ) : (
        <></>
      )}

      {/* 하단 고정 바 */}
      <View style={styles.lowerContainer}>
        {props.region.guideRoute.length > 0 &&
        props.trackingStatusProps.trackingStatus == 0 ? (
          <StartWithGuideButton
            setTrackingStatus={props.trackingStatusProps.setTrackingStatus}
            setGuideRoute={props.region.setGuideRoute}
          />
        ) : props.trackingStatusProps.trackingStatus === 0 ? (
          <StartButton
            setTrackingStatus={props.trackingStatusProps.setTrackingStatus}
          />
        ) : (
          <ProgressBar {...props.progressProps} />
        )}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    flex: 1,
    width: "100%",
    height: "100%",
  },
  upperContainer: {
    backgroundColor: "rgba(255, 255, 255, 0.95)",
    flexDirection: "row",
    paddingTop: 20,
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 5,
    justifyContent: "space-between",
    alignItems: "center",
    alignSelf: "stretch",
  },
  greetingContainer: {
    flexDirection: "row",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  greetingText: {
    color: colors.darkbrown,
    fontFamily: "font5",
    fontSize: 16,
    fontStyle: "normal",
  },
  nameContainer: {
    flexDirection: "row",
    alignItems: "center",
    rowGap: 3,
    columnGap: 3,
  },
  lowerContainer: {
    position: "absolute",
    bottom: 10,
    flexDirection: "row",
    width: "100%",
    justifyContent: "center",
    alignItems: "center",
  },
});

export default TrackingMainTpl;
