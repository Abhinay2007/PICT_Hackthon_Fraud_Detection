import UploadPage from "./components/UploadPage";
import Dashboard from "./components/Dashboard";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

const appRouter = createBrowserRouter([
  {
    path: "",
    element: <UploadPage />
  },
  {
    path: "/",
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
