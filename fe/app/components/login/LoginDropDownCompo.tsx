import colors from "@/assets/color.js";
import React from "react";
import { StyleSheet, Text, View, TextInput } from "react-native";
import { useState } from "react";
// import { SelectList } from "react-native-dropdown-select-list";
import DropDownPicker from "react-native-dropdown-picker";

// ★★★설치 : npm i react-native-dropdown-select-list
// ★★★설치 : npm i react-native-dropdown-picker

const LoginDropDownCompo = ({
  open,
  value,
  items,
  setOpen,
  setValue,
  setItems,
  placeholder,
}) => {
  return (
    <DropDownPicker
      open={open}
      value={value}
      items={items}
      setOpen={setOpen}
      setValue={setValue}
      setItems={setItems}
      placeholder={placeholder}
      closeOnBackPressed={true}
      style={{
        borderColor: colors.darkbrown,
        borderWidth: 1,
      }}
      textStyle={{
        fontSize: 16,
        fontFamily: "font3",
        color: colors.darkbrown,
      }}
      containerStyle={{
        zIndex: open ? 1000 : 0,
      }}
    />
  );
};

export default LoginDropDownCompo;

const styles = StyleSheet.create({
  container: {
    width: "100%",
    alignItems: "center",
    backgroundColor: "#ffffff",
  },
  inputBox: {
    width: "100%",
    paddingVertical: 10,
    textAlign: "center",
    alignContent: "center",
    borderBottomColor: colors.beige,
    borderBottomWidth: 1,
    fontFamily: "font3",
    fontSize: 16,
    color: colors.lightbeige,
  },
  text: {
    fontFamily: "font3",
    fontSize: 16,
    color: colors.lightbeige,
  },
});

// LoginDropDownCompo 선언해서 사용하기
/* 
 // 나이-LoginDropDownCompo 사용 위한 data 변수 선언
 const [openOfAge, setOpenOfAge] = useState(false);
 const [valueOfAge, setValueOfAge] = useState(null);
 const [itemsOfAge, setItemsOfAge] = useState([
   { label: "10대", value: "1" },
   { label: "20대", value: "2" },
   { label: "30대", value: "3" },
   { label: "40대", value: "4" },
   { label: "50대", value: "5" },
   { lable: "60대 이상", value: "6" },
 ]);

 // 성별-LoginDropDownCompo 사용 위한 data 변수 선언
 const [openOfGender, setOpenOfGender] = useState(false);
 const [valueOfGender, setValueOfGender] = useState(null);
 const [itemsOfGender, setItemsOfGender] = useState([
   { label: "남성", value: "M" },
   { label: "여성", value: "F" },
 ]);

 // 선호 소요시간-LoginDropDownCompo 사용 위한 data 변수 선언
 const [openOfTime, setOpenOfTime] = useState(false);
 const [valueOfTime, setValueOfTime] = useState(null);
 const [itemsOfTime, setItemsOfTime] = useState([
   { label: "30분 미만", value: 1 },
   { label: "30분 ~ 1시간", value: 2 },
   { label: "1시간 ~ 2시간", value: 3 },
   { label: "2시간 이상", value: 4 },
 ]);

 // 선호 거리-LoginDropDownCompo 사용 위한 data 변수 선언
 const [openOfDistance, setOpenOfDistance] = useState(false);
 const [valueOfDistance, setValueOfDistance] = useState(null);
 const [itemsOfDistance, setItemsOfDistance] = useState([
   { label: "1km 미만", value: 1000 },
   { label: "2km 미만", value: 2000 },
   { label: "2km 이상", value: 3000 },
 ]);

<LoginDropDownCompo
open={openOfAge}
value={valueOfAge}
items={itemsOfAge}
setOpen={setOpenOfAge}
setValue={setValueOfAge}
setItems={setItemsOfAge}
placeholder={"나이"}
/>
<LoginDropDownCompo
open={openOfGender}
value={valueOfGender}
items={itemsOfGender}
setOpen={setOpenOfGender}
setValue={setValueOfGender}
setItems={setItemsOfGender}
placeholder={"성별"}
/>
 */
