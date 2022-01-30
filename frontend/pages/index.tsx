import type { NextPage } from "next";
import Head from "next/head";
import UrlForm from "../components/UrlForm";

const Home: NextPage = () => {
  return (
    <div>
      <Head>
        <title>tinyurl</title>
        <link rel="icon" href="/app/favicon.ico" />
      </Head>
      <main className="container d-flex" style={{ height: "100vh" }}>
        <UrlForm />
      </main>
    </div>
  );
};

export default Home;
