import UploadPage from "./components/UploadPage";
import Dashboard from "./components/Dashboard";
import LandingPage from "./components/LandingPage";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

const appRouter = createBrowserRouter([
  {
    path: "",
    element: <LandingPage />
  },
  {
    path: "/",
    element: <LandingPage />
  },
  {
    path: "/upload",
    element: <UploadPage />
  },
  {
    path: "/dashboard",
    element: <Dashboard />
  }
])

export default function App() {
  return (
    <div>
      <RouterProvider router={appRouter}></RouterProvider>
    </div>
  );
}