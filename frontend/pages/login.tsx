import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import { toast, ToastContainer } from "react-toastify";
import axios from "axios";
import { useRouter } from "next/router";
import Router from "next/router";
import api from "../api";
import { useAppDispatch } from "../hooks";
import { setLogIn } from "../state/slices/loginSlice";

export default function Login() {
  const dispatch = useAppDispatch();

  const router = useRouter();

  React.useEffect(() => {
    toast(router.query.message, {
      // @ts-ignore
      type: router.query.type,
    });
    if (localStorage.getItem("token") !== null) {
      dispatch(setLogIn(true));
      Router.push({
        pathname: "/",
        query: {
          message: "already logged in",
          type: "error",
        },
      });
    }
  }, [router.query]);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    // eslint-disable-next-line no-console
    const formData = {
      username: data.get("username"),
      password: data.get("password"),
    };

    await axios
      .post(api + "/auth/authenticate", formData)
      .then(({ data }) => {
        localStorage.setItem("token", data.token);
        dispatch(setLogIn(true));
        Router.push({
          pathname: "/",
        });
      })
      .catch(({ response }) => {
        if (!response.data.message) {
          toast.error("invalid credentials");
          dispatch(setLogIn(false));
          return;
        }
        response.data.message.map((pair: string) => {
          // @ts-ignore
          toast.error(pair);
        });
      });
  };

  return (
    <main className="container d-flex" style={{ height: "100vh" }}>
      <div className="justify-content-center align-self-center mx-auto mb-5">
        <div className="form">
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
            className="auth-box"
          >
            <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Log In
            </Typography>
            <Box
              component="form"
              noValidate
              onSubmit={handleSubmit}
              sx={{ mt: 3 }}
            >
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="username"
                    label="Username"
                    name="username"
                    autoComplete="username"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="new-password"
                  />
                </Grid>
              </Grid>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Log In
              </Button>
              <Grid container justifyContent="flex-end">
                <Grid item>
                  <Link href="/app" variant="body1">
                    Proceed without log in
                  </Link>
                </Grid>
              </Grid>
              <Grid container justifyContent="flex-end">
                <Grid item>
                  <Link href="/app/register" variant="body2">
                    Dont't have an account? Register
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </Box>
        </div>
      </div>
      <ToastContainer />
    </main>
  );
}
