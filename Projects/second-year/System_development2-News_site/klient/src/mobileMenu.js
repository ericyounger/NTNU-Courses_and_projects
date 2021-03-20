import * as React from 'react';
import { Component } from 'react-simplified';
import {Menu} from "./header";

/** MobileMenu component is child of Header component, it only display the mobilemenu when screen size is less than threshold.
 * Does this by changing the props.visible from parent(Header) */
export class MobileMenu extends Component <{direction : string}>{
    render(){
        if(this.props.visible){
        return(
             <div class="mobileNavBar-active">
                <Menu direction="vertical"/>
             </div>
        )
        } else {
            return <div></div>
        }
    }

    mounted(): void {

    }

}