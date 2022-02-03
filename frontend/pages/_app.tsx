import "bootstrap/dist/css/bootstrap.css";
import "../styles/globals.css";
import "react-toastify/dist/ReactToastify.css";
import type { AppProps } from "next/app";
import Script from "next/script";
import Head from "next/head";
import { Provider } from "react-redux";
import store from "../state/store";
import NavBar from "../components/NavBar";

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <Provider store={store}>
      <Head>
        <title>Tinyurl</title>
        <link rel="icon" href="/app/favicon.ico" />
      </Head>
      <Component {...pageProps} />
      <Script src="https://kit.fontawesome.com/212c3a6f5a.js"></Script>
    </Provider>
  );
}

export default MyApp;
