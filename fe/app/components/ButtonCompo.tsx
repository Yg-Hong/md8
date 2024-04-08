import { StyleSheet, Text, TouchableOpacity, View } from "react-native";

export default function ButtonCompo({
  mode,
  textGreen,
  textWhite,
  onPressGreen,
  onPressWhite,
}) {
  switch (mode) {
    case "ONE_BUTTON":
      return (
        <View style={styles.wrapper}>
          <TouchableOpacity
            style={{ ...styles.button, backgroundColor: "#94C020" }}
            activeOpacity={0.9}
            onPress={onPressGreen}
          >
            <Text style={styles.buttonText}>
              {textGreen ? textGreen : "버튼명 입력"}
            </Text>
          </TouchableOpacity>
        </View>
      );
    case "TWO_BUTTONS":
      return (
        <View style={styles.wrapper}>
          <TouchableOpacity
            style={{
              ...styles.button,
              backgroundColor: "#ffffff",
            }}
            activeOpacity={0.6}
            onPress={onPressWhite}
          >
            <Text style={{ ...styles.buttonText, color: "#000000" }}>
              {textWhite ? textWhite : "버튼명 입력"}
            </Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={{ ...styles.button, backgroundColor: "#94C020" }}
            activeOpacity={0.9}
            onPress={onPressGreen}
          >
            <Text style={styles.buttonText}>
              {textGreen ? textGreen : "버튼명 입력"}
            </Text>
          </TouchableOpacity>
        </View>
      );
    default:
      return <></>;
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flexDirection: "row",
    gap: 10,
    justifyContent: "space-between",
  },
  button: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    borderWidth: 1,
    borderColor: "#94C020",
    paddingVertical: 10,
    fontSize: 40,
  },
  buttonText: {
    fontSize: 16,
    fontWeight: "600",
    color: "#ffffff",
    fontFamily: "font6",
  },
});
