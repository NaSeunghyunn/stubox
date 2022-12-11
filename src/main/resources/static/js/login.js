function setUserAgent() {
    const UA = navigator.userAgent;
    if (UA.toLowerCase().indexOf("chrome") < 0) {
        const userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Safari/537.36";
        Object.defineProperties(navigator, {
            userAgent: {
                get: () => userAgent,
            },
        });
    }
}

setUserAgent();
