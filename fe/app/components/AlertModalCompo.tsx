import React from "react";
import {
  Modal,
  View,
  Text,
  Image,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import colors from "@/assets/color.js";
import ButtonCompo from "./ButtonCompo";

interface AlertModalCompoProps {
  alertModalVisiable: boolean;
  setAlertModalVisiable: (value: boolean) => void;
  title: string;
  textWhite: string;
  textGreen: string;
  //Todo: 이벤트 전달인데 전달될 props를 아직 모름
  onPressCancel: (props?: any) => void;
  onPressConfirm: (props?: any) => void;
}

const AlertModalCompo = (props: AlertModalCompoProps) => {
  return (
    <Modal
      animationType="fade"
      transparent
      visible={props.alertModalVisiable}
      onRequestClose={() => {
        console.log("Modal has been closed.");
        props.setAlertModalVisiable(false);
      }}
    >
      {/* 배경 부분 */}
      <View
        onTouchEnd={() => {
          ``;
          props.setAlertModalVisiable(false);
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
          <View style={styles.titleContainer}>
            <Text style={styles.titleText}>{props.title}</Text>
          </View>

          <ButtonCompo
            mode="TWO_BUTTONS"
            textWhite={props.textWhite}
            textGreen={props.textGreen}
            onPressWhite={() => {
              props.onPressCancel();
            }}
            onPressGreen={() => {
              props.onPressConfirm();
            }}
          />
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
    width: 330,
    height: 280,
    paddingTop: 60,
    paddingLeft: 25,
    paddingRight: 25,
    paddingBottom: 60,
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 40,
    columnGap: 40,
    borderRadius: 25,
    backgroundColor: "white",
    shadowColor: "rgba(0, 0, 0, 0.250980406999588)",
    shadowRadius: 4,
    shadowOffset: { width: 0, height: 4 },
  },
  titleContainer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  titleText: {
    color: colors.darkbrown,
    textAlign: "center",
    fontFamily: "font5",
    fontSize: 20,
    fontStyle: "normal",
    fontWeight: "500",
    lineHeight: 24,
  },
});

export default AlertModalCompo;
