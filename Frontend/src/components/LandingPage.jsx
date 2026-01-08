import { ShieldCheck, ArrowRight } from "lucide-react";
import css from "../styles/LandingPage.module.css";
import { useNavigate } from "react-router-dom";

const LandingPage = () => {

    const navigate = useNavigate();

    const onGetStarted = () => {
        navigate("/upload")
    }

    return (
        <div className={css.wrapper}>
            <div className={css.glowBg} />

            <nav className={css.nav}>
                <div className={css.logo}>
                    <ShieldCheck size={28} />
                    <span>CERBERUS</span>
                </div>
            </nav>

            <main className={css.hero}>
                <h1 className={css.title}>
                    CERBERUS
                </h1>

                <h2 className={css.subtitle}>
                    The Three-Headed Guardian of Security
                </h2>

                <p className={css.tagline}>
                    Guarding Transactions at the Gates of Trust.
                </p>

                <p className={css.description}>
                    Cerberus is a next-generation fraud detection platform built on
                    <span> ML-powered glassbox explainability</span> — not opaque blackbox AI.
                    Each anomaly is identified through machine learning signals and translated
                    into clear, auditable explanations using a Tri-Shield defense system.
                </p>


                <div className={css.shields}>
                    <div className={css.shield}>Deterministic Signal Rules</div>
                    <div className={css.shield}>Machine Learning Detection</div>
                    <div className={css.shield}>Glassbox AI Explanations</div>
                </div>

                <p className={css.trustLine}>
                    No blackbox decisions — every alert is traceable, explainable, and review-ready.
                </p>


                <button className={css.cta} onClick={onGetStarted}>
                    Get Started
                    <ArrowRight />
                </button>
            </main>

            <footer className={css.footer}>
                © {new Date().getFullYear()} CERBERUS AI — All Rights Reserved
            </footer>
        </div>
    );
};

export default LandingPage;