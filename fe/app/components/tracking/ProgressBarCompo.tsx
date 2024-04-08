import React, { useEffect, useState } from "react";
import { View, Text, Button, StyleSheet } from "react-native";
import { TouchableOpacity } from "react-native-gesture-handler";
import { Stopwatch, Timer } from "react-native-stopwatch-timer";

import ButtonStop from "@/assets/icons/ButtonStop";
import ButtonPause from "@/assets/icons/ButtonPause";
import ButtonCamera from "@/assets/icons/ButtonCamera";
import ButtonStart from "@/assets/icons/ButtonStart";

import colors from "@/assets/color.js";
import { useNavigation } from "@react-navigation/native";

interface ProgressBarCompoProps {
  distance: number;
  time: number;
  kcal: number;
  trackingStatus: number;
  setTrackingStatus: (props: number) => void;
  setTime: (props: number) => void;
}

const ProgressBarCompo = (props: ProgressBarCompoProps) => {
  const [stopwatchStart, setStopwatchStart] = useState(false);
  const [currentTime, setCurrentTime] = useState(0);

  const navigation = useNavigation();

  useEffect(() => {
    if (props.trackingStatus === 1) {
      setStopwatchStart(true);
    } else {
      setStopwatchStart(false);
    }
  }, [props.trackingStatus]);

  const getFormattedTime = (time: string) => {
    // hh:mm:ss 형태를 분으로 변환
    const timeArray = time.split(":");
    const hour = parseInt(timeArray[0]);
    const minute = parseInt(timeArray[1]);
    const second = parseInt(timeArray[2]);
    const minuteTime = hour * 60 + minute;

    setCurrentTime(minuteTime);
  };

  const onPressStopButton = () => {
    console.log("stop");
    props.setTrackingStatus(3);

    console.log(currentTime);
    props.setTime(currentTime);
    //todo navigation goBack 이벤트 구현
  };

  const onPressPauseButton = () => {
    console.log("pause");
    props.setTrackingStatus(2);
    //todo tracking pause 이벤트 구현
  };

  const onPressStartButton = () => {
    console.log("start");
    props.setTrackingStatus(1);
    //todo tracking start 이벤트 구현
  };

  const onPressCameraButton = () => {
    console.log("camera");
    //todo 카메라 작동 동작 구현
    // navigation.navigate("TrackingCameraPage");
    // 원래는 카메라로 가야하는데 amulator에서 카메라가 안열려 그래서 갤러리로
    navigation.navigate("TrackingGalleryPage");
  };

  return (
    <View style={styles.rootContainer}>
      <View style={styles.upperContainer}>
        <View style={styles.smallTextContainer}>
          <Text style={styles.smallText}>{`${props.distance / 1000}km`}</Text>
        </View>
        <View style={styles.bigTextContainer}>
          <Stopwatch
            start={stopwatchStart}
            options={stopWatchOptions}
            getTime={getFormattedTime}
          />
        </View>
        <View style={styles.smallTextContainer}>
          <Text style={styles.smallText}>{`${props.kcal}kcal`}</Text>
        </View>
      </View>
      <View style={styles.lowerContainer}>
        <TouchableOpacity
          style={styles.lowerContainer}
          onPress={onPressStopButton}
        >
          <ButtonStop width={50} height={50} />
        </TouchableOpacity>

        {props.trackingStatus === 1 ? (
          <TouchableOpacity
            onPress={onPressPauseButton}
            style={styles.lowerContainer}
          >
            <ButtonPause width={65} height={65} />
          </TouchableOpacity>
        ) : (
          <TouchableOpacity
            onPress={onPressStartButton}
            style={styles.lowerContainer}
          >
            <ButtonStart width={65} height={65} />
          </TouchableOpacity>
        )}
        <TouchableOpacity
          onPress={onPressCameraButton}
          style={styles.lowerContainer}
        >
          <ButtonCamera width={50} height={50} />
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    paddingTop: 3,
    paddingLeft: 7,
    paddingRight: 7,
    paddingBottom: 3,
    flexDirection: "column",
    alignItems: "flex-start",
    borderRadius: 25,
    backgroundColor: "rgba(255, 255, 255, 0.9)",
    shadowColor: "rgba(0, 0, 0, 0.25)",
    shadowRadius: 4,
    shadowOffset: { width: 0, height: 4 },
  },
  upperContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    columnGap: 18,
    alignSelf: "stretch",
  },
  smallTextContainer: {
    flexDirection: "row",
    paddingTop: 18,
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 18,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  bigTextContainer: {
    flexDirection: "row",
    paddingTop: 5,
    paddingBottom: 2,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  smallText: {
    flexShrink: 0,
    color: colors.darkbrown,
    textAlign: "center",
    fontFamily: "font6",
    fontSize: 15,
  },
  bigText: {
    color: colors.green,
    textAlign: "center",
    fontFamily: "font6",
    fontSize: 28,
  },
  lowerContainer: {
    flexDirection: "row",
    paddingLeft: 6,
    paddingRight: 6,
    paddingBottom: 5,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 25,
    columnGap: 25,
    alignSelf: "stretch",
  },
  smallButtonContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  bigButtonContainer: {
    flexDirection: "row",
    paddingTop: 5,
    paddingLeft: 2,
    paddingRight: 2,
    paddingBottom: 5,
    justifyContent: "center",
    alignItems: "flex-start",
    rowGap: 10,
    columnGap: 10,
  },
});

const stopWatchOptions = {
  container: {
    backgroundColor: "rgba(255, 255, 255, 0.9)",
    flexDirection: "row",
    paddingTop: 10,
    paddingBottom: 10,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  text: {
    color: colors.green,
    textAlign: "center",
    fontFamily: "font6",
    fontSize: 28,
  },
};

export default ProgressBarCompo;
