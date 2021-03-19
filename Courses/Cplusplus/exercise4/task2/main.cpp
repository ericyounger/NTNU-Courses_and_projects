#include <gtkmm.h>

class Window : public Gtk::Window {
public:
    Gtk::VBox vbox;
    Gtk::Label first_name_label;
    Gtk::Label last_name_label;
    Gtk::Entry first_name_entry;
    Gtk::Entry last_name_entry;
    Gtk::Button button;
    Gtk::Label label;
    bool first_name_not_empty = false;
    bool last_name_not_empty = false;

    Window() {
        this->set_title("Exercise 4"); // Set window title
        button.set_label("Combine names"); //Set button label
        first_name_label.set_label("First name"); //Set first_name_entry label
        last_name_label.set_label("Last name"); // Set last_name_entry label.

        vbox.pack_start(first_name_label); // Add widget label to vbox
        vbox.pack_start(first_name_entry);  //Add the widget entry to vbox
        vbox.pack_start(last_name_label); // Add widget label to vbox
        vbox.pack_start(last_name_entry);  //Add the widget entry to vbox
        vbox.pack_start(button); //Add the widget button to vbox
        vbox.pack_start(label);  //Add the widget label to vbox

        add(vbox);  //Add vbox to window
        show_all(); //Show all widgets

        //Disable button on start by default.
        button.set_sensitive(false);

        //EVENT HANDLERS

        //Onchange for firstname
        first_name_entry.signal_changed().connect([this]() {
            if(first_name_entry.get_text().empty()){
                first_name_not_empty = false;
                button.set_sensitive(false);
            } else {
                first_name_not_empty = true;
                if(last_name_not_empty){
                    button.set_sensitive(true);
                }
            }
        });

        //Onchange last name
        last_name_entry.signal_changed().connect([this]() {
            if(last_name_entry.get_text().empty()){
                last_name_not_empty = false;
                button.set_sensitive(false);
            } else {
                last_name_not_empty = true;
                if(first_name_not_empty){
                    button.set_sensitive(true);
                }
            }
        });

        //Button click
        button.signal_clicked().connect([this]() {
            label.set_text("Names combined: " + first_name_entry.get_text() + " " + last_name_entry.get_text());
        });
    }
};

int main() {
    Gtk::Main gtk_main;
    Window window;
    gtk_main.run(window);
}
