import * as React from "react";
import { useSelector } from "react-redux";
import { useAppDispatch } from "../hooks";
import { selectLogIn, setLogIn } from "../state/slices/loginSlice";
import UrlsSideBar from "./UrlSideBar";

const NavBar = () => {
  const isLoggedIn = useSelector(selectLogIn);
  const [navMenuJSX, setNavMenuJSX] = React.useState<JSX.Element>();
  const [showSideBar, setShowSideBar] = React.useState(false);

  const dispatch = useAppDispatch();

  const myUrlsButton = (
    <button
      className="btn btn-primary"
      onClick={() => setShowSideBar((prev) => !prev)}
      role="button"
    >
      My Urls
    </button>
  );

  React.useEffect(() => {
    dispatch(setLogIn(localStorage.getItem("token") !== null));
  }, []);

  React.useEffect(() => {
    if (isLoggedIn) {
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
  }, [isLoggedIn]);

  return (
    <>
      <div className="nav-menu-container">{navMenuJSX}</div>
      <UrlsSideBar open={showSideBar} setOpen={setShowSideBar} />
    </>
  );
};
export default NavBar;
