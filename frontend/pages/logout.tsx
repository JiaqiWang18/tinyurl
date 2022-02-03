import type { NextPage } from "next";
import Router from "next/router";
import { useEffect } from "react";

const Logout: NextPage = () => {
  useEffect(() => {
    localStorage.removeItem("token");
    Router.push({
      pathname: "/",
      query: { message: "logout succeed" },
    });
  }, []);

  return <div></div>;
};

export default Logout;
