import {useNavigate} from "react-router-dom";

export function AccountPage(){
    const nav = useNavigate();
    return(<div>
        <div className="general" id="generalBlock" style={{
            width: "100%",
            marginLeft: "0px",
            marginRight: "200px",
            borderRadius: "15px",
            backgroundColor: "#ffffff",
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)",
            verticalAlign: "top",
            fontFamily: "inherit",
            // minHeight: "300px",
            minHeight: "300px",
        }}>
            <button onClick={() => nav('/')} className="btn-new btnLogIn">Log out</button>
            <button onClick={() => nav('/info')} className="btn-new btnLogIn">Information</button>
            <button onClick={() => nav('/subscribe')} className="btn-new btnLogIn">Subscribe</button>
            <div className="logo">
                <img
                    className="imgLogo"
                    src="media/logo.png"
                    alt="Notification System"
                    style={{
                        marginTop: "2rem",
                        width: "50%",
                        display: "block",
                        marginLeft: "auto",
                        marginRight: "auto",
                        background: "#ffffff",
                        borderRadius: "5px",
                        padding: "2%",
                    }}
                />
            </div>
            <p>Username: ...</p>
            <p>Mail: ...</p>
            <p>Telegram: ...</p>
            <p>VK: ...</p>
        </div>
    </div>)
}