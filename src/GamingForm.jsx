import React, { useState } from "react";
import "./GamingForm.css";  // External CSS

const GamingForm = () => {
  const [form, setForm] = useState({
    username: "",
    email: "",
    mobileno: "",
    pass: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8080/api/save", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form)
    });

    if (response.ok) {
      alert("User Registered Successfully!");
      setForm({ username: "", email: "", mobileno: "", pass: "" });
    } else {
      alert("Something went wrong!");
    }
  };

  return (
    <div className="game-bg">
      <form className="game-form" onSubmit={handleSubmit}>
        <h2 id="heading">Gaming Gear / Signup</h2>

        <input
          type="text"
          name="username"
          placeholder="Enter Username"
          value={form.username}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Enter Email"
          value={form.email}
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="mobileno"
          placeholder="Enter Mobile Number"
          value={form.mobileno}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="pass"
          placeholder="Enter Password"
          value={form.pass}
          onChange={handleChange}
          required
        />

        <button className="btn" type="submit">Register</button>
      </form>
    </div>
  );
};

export default GamingForm;
