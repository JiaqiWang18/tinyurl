import type { NextPage } from "next";
import { useRouter } from "next/router";
import { useEffect } from "react";
import UrlForm from "../components/UrlForm";

import { toast, ToastContainer } from "react-toastify";

const Home: NextPage = () => {
  const router = useRouter();

  useEffect(() => {
    toast.success(router.query.message);
  }, [router.query.message]);

  return (
    <div>
      <main className="container d-flex" style={{ height: "100vh" }}>
        <UrlForm />
      </main>
      <ToastContainer />
    </div>
  );
};

export default Home;
