// @flow
import * as React from 'react';
import { Component } from 'react-simplified';
import { NavLink } from 'react-router-dom';
import {Button} from "./widgets";
import {articleService, mobileDisplay} from "./services";
import {MobileMenu} from "./mobileMenu";
import io from "socket.io-client";


/** Menu component display the menu on the webpage, it sets up vertically or horizontally
 * based on screen size, if less than threshold, display vertically (for mobiles), else horizontally.
 * Has Header component as parent component. Parent decides to display menu for mobile or not. */
export class Menu extends Component <{direction : string}>{
    li = "";
    ul = "";
    uniqueKey = 0;

    render(){
        return(
            <div>
                <ul className={this.ul}>
                    <NavLink to="/">
                        <li key={"MobileMenuDisplay"+this.uniqueKey} className={this.li} onClick={mobileDisplay.setDisplayFalse}>Topp</li>
                    </NavLink>

                    {articleService.categories.map((e,index) =>
                        <NavLink to= {"/category/" + e.category.toLowerCase()}>
                            <li key={"uniqueCategory"+index+this.uniqueKey} className={this.li} onClick={mobileDisplay.setDisplayFalse}>{e.category}</li>
                        </NavLink>
                    )}

                    <NavLink to="/registrer">
                        <li key={this.uniqueKey+"registrer"} className={this.li + " navButton"} onClick={mobileDisplay.setDisplayFalse}>
                            <Button.Light onClick={()=>{}}>Registrer</Button.Light>
                        </li>
                    </NavLink>
                </ul>
            </div>
        )
    }

    mounted(): void {
        //Fetches all categories to map from.
        articleService.getCategories();

        //Sets the orientation of the menu based on screen size.
        if(this.props.direction === "vertical"){
            this.li = "navMobileLi";
            this.ul = "navMobileUl";
            this.uniqueKey = 1;

        } else if(this.props.direction === "horizontal"){
            this.li = "navLi";
            this.ul = "navUl";
            this.uniqueKey = 2;
        }
    }
}

/** Header is parent component for 'Menu' and 'LiveFeed'. Sets up menu based on screen size (for mobile or desktop screens)
 * Also holds the LiveFeed component in place. */
export class Header extends Component{
    render(){
        return(
            <div className="fixed-top">
                <NavLink to="/">
                <div className="logoHeader" onClick={mobileDisplay.setDisplayFalse}></div>
                </NavLink>

                <div className="menuHeader">
                  <Menu direction="horizontal"/>
                  <div onClick={mobileDisplay.toggleMobileMenu} id="burger">
                    <div className="pancakeMenu" id="pancake">
                        <div className="line"></div>
                        <div className="line"></div>
                        <div className="line"></div>
                    </div>
                  </div>
                </div>

                <LiveFeed/>
                <div className="mobileNavBar"></div>
                <MobileMenu visible={mobileDisplay.showMobileMenu}/>
            </div>
        )
    }

    mounted(): void {
        //Listens on browser window resize, and if less than 904px sets mobile display to false
        window.addEventListener('resize', () =>{
           if(window.innerWidth > 904){
               mobileDisplay.setDisplayFalse()
           }

        });
    }
    beforeUnmount(): void {
        //Remove listener on unmount, to avoid RAM feeding frenzy
        window.removeEventListener('resize', mobileDisplay.setDisplayFalse());
    }


}

/** LiveFeed component shows all headlines and post time for the newest articles.
 * Does this by connecting to socketserver, and gets a message to update articles if there are any new ones */
class LiveFeed extends Component{
    value : number= 60;

    constructor(props){

        super(props);

        let socket = io('http://localhost:4000');    //Connection to socket
        socket.on('connect', function(){console.log("connected")}); //Console welcome message
        socket.on('event', function(data){});
        socket.on('newArticle', e => { // Gets a message that there are new articles and forces a re-render which fetches new articles
            console.log("newArticle")
            articleService.newPosts = true;
        });
        socket.on('disconnect', function(){});
    }

    render(){
        if(articleService.newPosts){
            articleService.getAllArticles();
            articleService.newPosts = false;
        }
        return(
            <div>
                <div className="rollingNews">
                    <div id="rollText">
                        {articleService.articles.map(e => `${e.headline} - ${e.post_date.substring(11, 16)}  // `)}
                    </div>
                </div>
            </div>
        )
    }

    mounted() : void{
        // Move the x-position of the livefeed text every 15 ms.
        setInterval(a => this.animateLiveFeed(),15);
        articleService.getAllArticles();
    }

    //Sets the x-position of the liveFeed text by constantly decreasing up until a point before it resets.
    animateLiveFeed() : void{
        if(this.value<-70){
            this.value = 60;
        }

        let rollingNews : HTMLElement  = document.querySelector("#rollText");
        rollingNews.style.transform = `translateX(${this.value}%)`;
        this.value -= 0.05;

    }
}