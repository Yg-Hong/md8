import MenuBarCompo from "@/components/MenuBarCompo";
import React, { useEffect, useState } from "react";
import { View, Text } from "react-native";

import axios from "axios";

const BlockedUserPage = () => {
  const [blockedUserList, setBlockedUserList] = useState([]);

  const getBlockedUserList = async () => {
    await axios
      .get(`http://j10a208a.p.ssafy.io:8080/api/follows/block/${1}`)
      .then((res) => {
        console.log(res.data.data);
        setBlockedUserList(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    getBlockedUserList();
  }, []);

  return (
    <View>
      <MenuBarCompo title="차단 계정" settingButton={false} />
      <Text>BlockedUserPage</Text>
    </View>
  );
};

export default BlockedUserPage;
