const eslintPluginPrettierRecommended = require("eslint-plugin-prettier/recommended");

module.exports = {
  eslintPluginPrettierRecommended,
  extends: [
    "eslint:recommended",
    "@react-native-community",
    "airbnb",
    "airbnb/hooks",
    "plugin:prettier/recommended",
    "plugin:import/recommended",
  ],
  plugins: [
    "@typescript-eslint",
    "react",
    "react-native",
    "react-hooks",
    "prettier",
    "import",
  ],
  rules: {
    "prettier/prettier": [
      "error",
      {
        endOfLine: "auto",
      },
    ],
  },
};
