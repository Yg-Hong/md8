import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View } from "react-native";
import WaterLight from "@icons/WaterLight.svg";
import StoreLight from "@icons/StoreLight.svg";
import ToiletLight from "@icons/ToiletLight.svg";
import WaterNo from "@icons/WaterNo.svg";
import StoreNo from "@icons/StoreNo.svg";
import ToiletNo from "@icons/ToiletNo.svg";

const DudurimInfo = ({ mode, level, distance, time, water, store, toilet }) => {
  switch (mode) {
    case "GREEN":
      return (
        <View style={styles.greenContainer}>
          <View style={styles.infoWrapper}>
            <View style={styles.oneLine}>
              <Text style={styles.titleFixedSize}>난이도</Text>
              <Text style={styles.boldText}>{level}</Text>
            </View>
            <View style={styles.oneLine}>
              <Text style={styles.titleFixedSize}>코스길이</Text>
              <Text style={styles.boldText}>{distance}km</Text>
            </View>
            <View style={styles.oneLine}>
              <Text style={styles.titleFixedSize}>소요시간</Text>
              <Text style={styles.boldText}>{time}</Text>
            </View>
          </View>
          <View style={styles.iconWrapper}>
            {water ? (
              <WaterLight width={30} height={30} />
            ) : (
              <WaterNo width={30} height={30} />
            )}
            {store ? (
              <StoreLight width={30} height={30} />
            ) : (
              <StoreNo width={30} height={30} />
            )}
            {toilet ? (
              <ToiletLight width={30} height={30} />
            ) : (
              <ToiletNo width={30} height={30} />
            )}
          </View>
        </View>
      );
    case "WHITE":
      return (
        <View style={styles.whiteContainer}>
          <View style={styles.infoWrapper}>
            <View style={styles.oneLine}>
              <Text style={styles.titleFixedSize}>난이도</Text>
              <Text style={styles.boldText}>{level}</Text>
            </View>
            <View style={styles.oneLine}>
              <Text style={styles.titleFixedSize}>코스길이</Text>
              <Text style={styles.boldText}>{distance}km</Text>
            </View>
            <View style={styles.oneLine}>
              <Text style={styles.titleFixedSize}>소요시간</Text>
              <Text style={styles.boldText}>{time}</Text>
            </View>
          </View>
          <View style={styles.iconWrapper}>
            {water ? (
              <WaterLight width={30} height={30} />
            ) : (
              <WaterNo width={30} height={30} />
            )}
            {store ? (
              <StoreLight width={30} height={30} />
            ) : (
              <StoreNo width={30} height={30} />
            )}
            {toilet ? (
              <ToiletLight width={30} height={30} />
            ) : (
              <ToiletNo width={30} height={30} />
            )}
          </View>
        </View>
      );
    default:
      return <></>;
  }
};

export default DudurimInfo;

const styles = StyleSheet.create({
  greenContainer: {
    flex: 1,
    width: "100%",
    flexDirection: "row",
    paddingTop: 15,
    alignItems: "flex-start",
    backgroundColor: colors.lightgreen,
  },
  whiteContainer: {
    flex: 1,
    width: "100%",
    flexDirection: "row",
    padding: 15,
    alignItems: "flex-start",
    backgroundColor: "#ffffff",
  },
  infoWrapper: {
    flex: 6,
  },
  oneLine: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "flex-start",
  },
  titleFixedSize: {
    width: 70,
    textAlign: "left",
    fontFamily: "font4",
    fontSize: 14,
    color: colors.darkbrown,
  },
  boldText: {
    fontFamily: "font6",
    fontSize: 14,
    color: colors.darkbrown,
  },
  iconWrapper: {
    flex: 4,
    flexDirection: "row",
    gap: 7,
    justifyContent: "flex-end",
  },
});
