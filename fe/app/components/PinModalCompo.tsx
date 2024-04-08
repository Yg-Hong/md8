import React from "react";
import {
  Modal,
  View,
  Text,
  Image,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import PinCarouselCompo from "./PinCarouselCompo";

import ArrowLeftWhite from "@icons/ArrowLeftWhite";
import ArrowRightWhite from "@icons/ArrowRightWhite";
import Cancel from "@icons/Cancel";

interface Pin {
  imageId: number;
  authorId: number;
  uri: string;
}

interface PinModalCompoProps {
  pinModalVisiable: boolean;
  setPinModalVisiable: (value: boolean) => void;
  images: Pin[];
}

const PinModalCompo = (props: PinModalCompoProps) => {
  const [carouselIndex, setCarouselIndex] = React.useState(0);

  return (
    <Modal
      animationType="fade"
      transparent
      visible={props.pinModalVisiable}
      onRequestClose={() => {
        console.log("Modal has been closed.");
        props.setPinModalVisiable(false);
        setCarouselIndex(0);
      }}
    >
      {/* 배경 부분 */}
      <View
        onTouchEnd={() => {
          props.setPinModalVisiable(false);
          setCarouselIndex(0);
        }}
        style={styles.backgroundViewContainer}
      >
        {/* 모달 부분 */}
        <View
          onTouchEnd={(e) => {
            e.stopPropagation();
          }}
          style={styles.modalViewContainer}
        >
          <PinCarouselCompo
            pins={props.images}
            index={carouselIndex}
            setCarouselIndex={setCarouselIndex}
          />
          <View style={styles.arrowContainer}>
            <TouchableOpacity
              onPress={() => {
                if (carouselIndex > 0) {
                  setCarouselIndex(carouselIndex - 1);
                }
              }}
            >
              <ArrowLeftWhite />
            </TouchableOpacity>
            <TouchableOpacity
              onPress={() => {
                if (carouselIndex < props.images.length - 1) {
                  setCarouselIndex(carouselIndex + 1);
                }
              }}
            >
              <ArrowRightWhite />
            </TouchableOpacity>
          </View>

          <TouchableOpacity
            onPress={() => {
              props.setPinModalVisiable(false);
              setCarouselIndex(0);
            }}
            style={styles.cancelContainer}
          >
            <Cancel />
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  backgroundViewContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  modalViewContainer: {
    backgroundColor: "rgba(0, 0, 0, 0)",
    borderRadius: 25,
    width: "80%",
    height: "80%",
    alignItems: "center",
    elevation: 5,
  },
  pinImageContainer: {
    width: "100%",
    height: "100%",
    borderRadius: 25,
  },
  arrowContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "100%",
    paddingLeft: 10,
    paddingRight: 10,
    alignItems: "center",
    bottom: 250,
  },
  cancelContainer: {
    position: "absolute",
    flexDirection: "row",
    width: 20,
    height: 20,
    right: 16,
    top: 16,
  },
});

export default PinModalCompo;
