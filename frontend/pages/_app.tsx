import "bootstrap/dist/css/bootstrap.css";
import "../styles/globals.css";
import "react-toastify/dist/ReactToastify.css";
import type { AppProps } from "next/app";
import Script from "next/script";
import Head from "next/head";
import { useEffect, useState } from "react";
import { useRouter } from "next/router";
import UrlsSideBar from "../components/UrlSideBar";

function MyApp({ Component, pageProps }: AppProps) {
  const router = useRouter();

  const [navMenuJSX, setNavMenuJSX] = useState<JSX.Element>();
  const [showSideBar, setShowSideBar] = useState(false);
  const myUrlsButton = (
    <button
      className="btn btn-primary"
      onClick={() => setShowSideBar((prev) => !prev)}
      role="button"
    >
      My Urls
    </button>
  );
  useEffect(() => {
    if (localStorage.getItem("token") !== null) {
      setNavMenuJSX(
        <>
          {myUrlsButton}
          <a className="btn btn-primary" href="/app/logout" role="button">
            Log Out
          </a>
        </>
      );
    } else {
      setNavMenuJSX(
        <>
          <a className="btn btn-primary" href="/app/login" role="button">
            Log In
          </a>
          <a className="btn btn-primary" href="/app/register" role="button">
            Register
          </a>
          {myUrlsButton}
        </>
      );
    }
  }, [router.query.message]);
  return (
    <>
      <Head>
        <title>Tinyurl</title>
        <link rel="icon" href="/app/favicon.ico" />
      </Head>
      <div className="nav-menu-container">{navMenuJSX}</div>
      <UrlsSideBar open={showSideBar} setOpen={setShowSideBar} />

      <Component {...pageProps} />
      <Script src="https://kit.fontawesome.com/212c3a6f5a.js"></Script>
    </>
  );
}

export default MyApp;
