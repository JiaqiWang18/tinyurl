import type { NextPage } from "next";
import Router from "next/router";
import { useEffect } from "react";
import { useAppDispatch } from "../hooks";
import { setLogIn } from "../state/slices/loginSlice";

const Logout: NextPage = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    localStorage.removeItem("token");
    dispatch(setLogIn(false));
    Router.push({
      pathname: "/",
    });
  });

  return <div></div>;
};

export default Logout;
