import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { AppState } from "../store";

interface LoginSliceState {
  isLoggedIn: boolean;
}

const initialState: LoginSliceState = {
  isLoggedIn: false,
};

const loginSlice = createSlice({
  name: "login",
  initialState,
  reducers: {
    setLogIn: (state, action: PayloadAction<boolean>) => {
      state.isLoggedIn = action.payload;
    },
  },
});

export const { setLogIn } = loginSlice.actions;
export const selectLogIn = (state: AppState) => state.login.isLoggedIn;
export default loginSlice.reducer;
