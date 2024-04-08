import colors from "@/assets/color.js";
import { StyleSheet, View } from "react-native";
import Like from "@icons/Like.svg";

export default function LikeButton() {
  return (
    <View style={styles.container}>
      <Like width={25} height={25}></Like>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    width: 26,
    height: 26,
    justifyContent: "center",
    alignItems: "center",
    zIndex: 999,
  },
});
