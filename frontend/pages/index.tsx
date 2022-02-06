import type { NextPage } from "next";
import { useRouter } from "next/router";
import { useEffect } from "react";
import UrlForm from "../components/UrlForm";

import { toast, ToastContainer } from "react-toastify";
import NavBar from "../components/NavBar";

const Home: NextPage = () => {
  const router = useRouter();

  useEffect(() => {
    toast(router.query.message, {
      // @ts-ignore
      type: router.query.type,
    });
  }, [router.query]);

  return (
    <div>
      <NavBar />
      <main className="container d-flex" style={{ height: "100vh" }}>
        <UrlForm />
      </main>
      <ToastContainer />
    </div>
  );
};

export default Home;
