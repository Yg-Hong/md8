import React from "react";
import { View, Text, StyleSheet } from "react-native";
import colors from "@/assets/color";

import IconKcal from "@/assets/icons/IconKcal";
import IconDistance from "@/assets/icons/IconDistance";
import IconTime from "@/assets/icons/IconTime";

interface TrackingInfoCompoProps {
  distance: number;
  kcal: number;
  time: number;
}

const TrackingInfoCompo = (props: TrackingInfoCompoProps) => {
  const [hour, setHour] = React.useState<number>(0);
  const [minute, setMinute] = React.useState<number>(0);

  React.useEffect(() => {
    setHour(Math.floor(props.time / 60));
    setMinute(props.time % 60);
  }, [props.time]);

  return (
    <View style={styles.rootContainer}>
      <View style={styles.titleContainer}>
        <Text style={styles.titleText}>{`산책 정보`}</Text>
      </View>
      <View style={styles.informationContainer}>
        <View style={styles.row}>
          <View style={styles.inforamtionItemContainer}>
            <View>
              <IconDistance />
            </View>
            <View style={styles.TextContainer}>
              <Text style={styles.Text}>{`${props.distance / 1000}km`}</Text>
            </View>
          </View>
          <View style={styles.inforamtionItemContainer}>
            <View>
              <IconKcal />
            </View>
            <View style={styles.TextContainer}>
              <Text style={styles.Text}>{`${props.kcal}kcal`}</Text>
            </View>
          </View>
        </View>
        <View style={styles.row}>
          <View style={styles.inforamtionItemContainer}>
            <View>
              <IconTime />
            </View>
            <View style={styles.TextContainer}>
              <Text style={styles.Text}>{`${hour}h ${minute}m`}</Text>
            </View>
          </View>
          <View style={styles.inforamtionItemContainer}></View>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    width: "100%",
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
    paddingTop: 0,
    paddingLeft: 5,
    paddingRight: 5,
    paddingBottom: 0,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
  informationContainer: {
    height: 95,
    paddingTop: 10,
    paddingBottom: 10,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 5,
    columnGap: 5,
    alignSelf: "stretch",
    borderRadius: 8,
    backgroundColor: colors.lightbeige,
  },
  row: {
    flexDirection: "row",
    paddingLeft: 5,
    paddingRight: 5,
    justifyContent: "space-between",
    alignItems: "center",
    alignSelf: "stretch",
  },
  inforamtionItemContainer: {
    flexDirection: "row",
    paddingTop: 4,
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 4,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    flexGrow: 1,
  },
  Text: {
    color: colors.darkbrown,
    fontFamily: "font5",
    fontSize: 16,
  },
  TextContainer: {
    flexDirection: "row",
    paddingTop: 2,
    paddingBottom: 2,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
});

export default TrackingInfoCompo;
