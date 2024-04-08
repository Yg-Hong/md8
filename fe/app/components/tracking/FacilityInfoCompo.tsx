import React from "react";
import { View, Text, StyleSheet } from "react-native";
import colors from "@/assets/color";

import IconDrinking from "@/assets/icons/IconDrinking";
import IconSafezone from "@/assets/icons/IconSafezone";
import IconConvenience from "@/assets/icons/IconConvenience";
import IconToilet from "@/assets/icons/IconToilet";

interface FacilityInfoCompoProps {
  drinking: number;
  toilet: number;
  safezone: number;
}

const FacilityInfoCompo = (props: FacilityInfoCompoProps) => {
  return (
    <View style={styles.rootContainer}>
      <View style={styles.titleContainer}>
        <Text style={styles.titleText}>{`편의시설 정보`}</Text>
      </View>
      <View style={styles.informationContainer}>
        <View style={styles.subTitleContainer}>
          <Text style={styles.subTitleText}>
            {`산책 경로에 이런 편의시설이 있어요`}
          </Text>
        </View>
        <View style={styles.rowContainer}>
          <View style={styles.row}>
            <View>
              <IconDrinking />
            </View>
            <View style={styles.textContainer}>
              <Text style={styles.text}>{`음수대 ${props.drinking}곳`}</Text>
            </View>
          </View>
          <View style={styles.row}>
            <View>
              <IconSafezone />
            </View>
            <View style={styles.textContainer}>
              <Text style={styles.text}>
                {`안전시킴이 시설 ${props.safezone}곳`}
              </Text>
            </View>
          </View>
          <View style={styles.row}>
            <View>
              <IconToilet />
            </View>
            <View style={styles.textContainer}>
              <Text style={styles.text}>{`화장실 ${props.toilet}곳`}</Text>
            </View>
          </View>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    width: "100%",
    flexDirection: "column",
    justifyContent: "center",
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
    paddingLeft: 5,
    paddingRight: 5,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
  subTitleText: {
    color: colors.darkbrown,
    fontFamily: "font3",
    fontSize: 16,
  },
  informationContainer: {
    paddingTop: 15,
    paddingLeft: 5,
    paddingRight: 5,
    paddingBottom: 15,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "flex-start",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
    borderRadius: 8,
    backgroundColor: colors.lightbeige,
  },
  subTitleContainer: {
    flexDirection: "row",
    paddingLeft: 10,
    paddingRight: 10,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
  rowContainer: {
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 3,
    columnGap: 3,
    alignSelf: "stretch",
  },
  textContainer: {
    flexDirection: "row",
    paddingTop: 1,
    paddingLeft: 0,
    paddingRight: 0,
    paddingBottom: 1,
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  frame26082173: {
    flexDirection: "row",
    paddingTop: 2,
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 2,
    alignItems: "center",
    rowGap: 7,
    columnGap: 7,
    alignSelf: "stretch",
  },
  text: {
    color: colors.darkbrown,
    fontFamily: "font3",
    fontSize: 16,
  },
  row: {
    flexDirection: "row",
    paddingTop: 2,
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 2,
    alignItems: "center",
    rowGap: 7,
    columnGap: 7,
    alignSelf: "stretch",
  },
});

export default FacilityInfoCompo;
