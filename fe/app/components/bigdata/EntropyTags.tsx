import { StyleSheet, Text, TouchableOpacity } from "react-native";

const EntropyTags = ({ congestion }) => {
  switch (congestion) {
    case 1:
      return (
        <TouchableOpacity activeOpacity={0.9} style={styles.levelOneButton}>
          <Text style={styles.buttonText}>여유</Text>
        </TouchableOpacity>
      );

    case 2:
      return (
        <TouchableOpacity activeOpacity={0.9} style={styles.levelTwoButton}>
          <Text style={styles.buttonText}>보통</Text>
        </TouchableOpacity>
      );
    case 3:
      return (
        <TouchableOpacity activeOpacity={0.9} style={styles.levelThreeButton}>
          <Text style={styles.buttonText}>약간붐빔</Text>
        </TouchableOpacity>
      );
    case 4:
      return (
        <TouchableOpacity activeOpacity={0.9} style={styles.levelFourButton}>
          <Text style={styles.buttonText}>붐빔</Text>
        </TouchableOpacity>
      );

    default:
      return <></>;
  }
};

export default EntropyTags;

const styles = StyleSheet.create({
  levelOneButton: {
    width: 80,
    height: 26,
    backgroundColor: "#00D369",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    fontSize: 12,
  },
  levelTwoButton: {
    width: 80,
    height: 26,
    backgroundColor: "#FFB100",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    fontSize: 12,
  },
  levelThreeButton: {
    width: 80,
    height: 26,
    backgroundColor: "#FF8040",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    fontSize: 12,
  },
  levelFourButton: {
    width: 80,
    height: 26,
    backgroundColor: "#DD1F3D",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8,
    fontSize: 12,
  },
  buttonText: {
    fontFamily: "font6",
    fontSize: 14,
    color: "#ffffff",
  },
});
