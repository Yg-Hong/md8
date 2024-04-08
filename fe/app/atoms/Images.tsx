import { StyleSheet, View, ImageBackground } from "react-native";

export default function Images({ src, width, height }) {
  const uri = { uri: src };
  const styles = StyleSheet.create({
    container: {
      width: width,
      height: height,
      // backgroundColor: "orange",
    },
    image: {
      width: "100%",
      height: "100%",
      flex: 1,
      justifyContent: "center",
    },
  });

  return (
    <View style={styles.container}>
      <ImageBackground
        source={uri}
        // source={src}
        style={styles.image}
        resizeMode="cover"
      ></ImageBackground>
    </View>
  );
}
