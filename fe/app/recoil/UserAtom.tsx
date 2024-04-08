import { atom } from "recoil";
import AsyncStorage from "@react-native-async-storage/async-storage";

// interface User {
//   token: string;
//   userId: number;
//   email: string;
//   nickName: string;
// }

export const EmailAtom = atom({
  key: "EmailAtom",
  default: "",
});

export const NicknameAtom = atom({
  key: "NicknameAtom",
  default: "",
});

export const ProfileAtom = atom({
  key: "ProfileAtom",
  default: "",
});

export const TokenAtom = atom({
  key: "TokenAtom",
  default: "",
});

export const UserIdAtom = atom({
  key: "UserIdAtom",
  default: -1,
});

// export const UserInfo = atom<User>({
//   key: "userInfo",
//   default: { token: "", userId: -1, email: "", nickName: "" },
// });
