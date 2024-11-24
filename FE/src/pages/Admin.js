import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import fetchAuthorizedPage from "../services/fetchAuthorizedPage";

const Admin = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [data, setData] = useState("");

    fetchAuthorizedPage(`${process.env.REACT_APP_API_BASE_URL}/api/v1/admin`, navigate, location)
        .then(result => setData(result));

    return data && <h2>{data}</h2>;
}

export default Admin;