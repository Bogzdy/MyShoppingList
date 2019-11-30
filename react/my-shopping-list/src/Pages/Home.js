import React, {Component} from "react";
import MyDrawer from "../Components/MyDrawer";

class Home extends Component{
    constructor(props) {
        super(props);

    }

    render() {
        return(
            <MyDrawer
                title={"Home"}
                arrayOfItems={["Home", "Products", "Users"]}
            />
        )
    }


}
export default Home;