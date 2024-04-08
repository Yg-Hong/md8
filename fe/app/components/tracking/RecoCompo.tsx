import React from "react";
import { Text, View, StyleSheet } from "react-native";
import colors from "@/assets/color.js";

import IconReco from "@/assets/icons/IconReco";
import IconNoReco from "@/assets/icons/IconNoReco";

interface RecoCompoProps {
  isReco: boolean;
}

const RecoCompo = (props: RecoCompoProps) => {
  return (
    <View style={styles.rootContainer} testID={props.testID}>
      <View style={styles.titleContainer} testID="836:1725">
        <Text style={styles.titleText} testID="836:1726">
          {`추천하기`}
        </Text>
      </View>
      <View style={styles.contentContainer} testID="836:1727">
        <View style={styles.iconContainer} testID="836:1728">
          {props.isReco ? <IconReco /> : <IconNoReco />}
        </View>
        <View style={styles.recoContainer} testID="836:1733">
          <Text style={styles.recoText} testID="836:1734">
            {props.isReco ? `추천 공개 중` : `추천 비공개 중`}
          </Text>
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
  titleContainer: {
    flexDirection: "row",
    paddingLeft: 5,
    paddingRight: 5,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    alignSelf: "stretch",
  },
  titleText: {
    color: colors.darkbrown,
    fontFamily: "font6",
    fontSize: 16,
  },
  contentContainer: {
    flexDirection: "row",
    width: "100%",
    paddingTop: 5,
    paddingLeft: 16,
    paddingRight: 16,
    paddingBottom: 5,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    borderRadius: 8,
    backgroundColor: colors.lightbeige,
  },
  iconContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "flex-start",
    rowGap: 10,
    columnGap: 10,
  },
  recoContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "flex-start",
    rowGap: 10,
    columnGap: 10,
  },
  recoText: {
    flexDirection: "column",
    justifyContent: "center",
    color: colors.darkbrown,
    fontFamily: "font3",
    fontSize: 16,
  },
});

export default RecoCompo;
