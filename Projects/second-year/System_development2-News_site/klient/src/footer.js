import * as React from 'react';
import { Component } from 'react-simplified';

/** Footer component is a footer bar at the bottom of the page, is displayed at all times, and has
 * three information boxes to store text in. */
export class Footer extends Component{
    render(){
        return(
            <div className="footerBar">
                <FooterBox>
                    Ansv. redaktør og adm. direktør: Tøys Tullesen<br/>
                    Nyhetsredaktør: Foren Fantasi<br/>
                    Utviklingsredaktør: Donald Duck

                </FooterBox>
                <FooterBox>
                    Tips oss! SMS / MMS til Vaffelsentralen <br/>
                    Ring 815 493 00<br/>
                    E-post til potet@thebriefing.no <br/>
                    Kontakt oss<br/>

                    <p>Annonseinfo</p>
                </FooterBox>

                <FooterBox>
                    Denne informasjonen er veldig viktig, og det er av høyeste viktighetsgrad
                    at denne informasjonen ikke blir glemt, så husk det!
                </FooterBox>
            </div>
        )
    }
}

/** FooterBox is a information box used on the footer bar */
export class FooterBox extends Component{
    render(){
        return(
            <div className="footerBox">
                {this.props.children}
            </div>
            )
    }
}