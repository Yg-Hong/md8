import colors from "@/assets/color.js";
import { StyleSheet, Text, View } from "react-native";

export default function Title({
  content,
  fontfamily = "font6",
  fontsize = 18,
  marginbottom = 0,
}) {
  const styles = StyleSheet.create({
    container: {
      justifyContent: "center",
      alignItems: "center",
      marginBottom: marginbottom,
    },
  });
  const dynamicStyles = StyleSheet.create({
    text: {
      alignItems: "center",
      justifyContent: "center",
      fontFamily: fontfamily,
      fontSize: fontsize,
      textAlign: "right",
      color: colors.darkbrown,
    },
  });

  return (
    <View style={styles.container}>
      <Text numberOfLines={1} style={dynamicStyles.text}>
        {content}
      </Text>
    </View>
  );
}
