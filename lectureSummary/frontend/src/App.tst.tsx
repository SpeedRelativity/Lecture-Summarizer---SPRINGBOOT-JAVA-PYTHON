import { render, screen } from "@testing-library/react";
import App from "./App";

test("renders upload heading", () => {
  render(<App />);
  expect(
    screen.getByText(/Upload Your File or Paste a Youtube Link/i)
  ).toBeInTheDocument();
});
