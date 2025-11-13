import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import Processing from "./pages/Processing.tsx";
import JobDetails from "./pages/JobDetails.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import NotFoundPage from "./pages/NotFoundPage.tsx";

const router = createBrowserRouter([
  { path: "/", element: <App /> },
  { path: "/submission", element: <Processing /> },
  { path: "/jobs/:jobId", element: <JobDetails /> },
  { path: "/*", element: <NotFoundPage /> },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
