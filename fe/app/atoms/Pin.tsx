import React from "react";
import { Text, View, Image, StyleSheet } from "react-native";

interface Item {
  imageId: number;
  authorId: number;
  uri: string;
}

const Pin = (props: Item) => {
  return (
    <View style={styles.pinImageContainer}>
      <Image
        source={{
          uri: props.uri,
        }}
        style={styles.pinImageComponent}
        resizeMode="cover"
      />
      {/*
        <Text>{`authorId : ${props.authorId}`}</Text>
        <Text>{`imageId : ${props.imageId}`}</Text>
       */}
    </View>
  );
};

const styles = StyleSheet.create({
  pinImageContainer: {
    backgroundColor: "rgba(128, 128, 128, 0)",
    borderRadius: 25,
    width: "100%",
    height: "100%",
    alignItems: "center",
    elevation: 3,
  },
  pinImageComponent: {
    width: "100%",
    height: "100%",
    borderRadius: 25,
  },
});

export default Pin;
