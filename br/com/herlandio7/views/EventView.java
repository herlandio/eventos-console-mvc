package br.com.herlandio7.views;

import java.util.List;

import br.com.herlandio7.models.Event;
import br.com.herlandio7.models.User;

public class EventView {
    /**
     * The `menu` function displays a menu with options for managing users and
     * events in a system.
     */
    public void menu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Cadastrar usuários");
        System.out.println("2 - Cadastrar eventos");
        System.out.println("3 - Listar usuários");
        System.out.println("4 - LIstar eventos");
        System.out.println("5 - Ordenar eventos por horário");
        System.out.println("6 - Eventos Acontecendo no momento");
        System.out.println("7 - Eventos que já passaram");
        System.out.println("8 - Participar de um evento");
        System.out.println("9 - Eventos confirmados");
        System.out.println("10 - Cancelar participação");
        System.out.println("0 - Sair");
    }

    /**
     * The function `allEvents` prints out details of events in a list, including
     * name, category, time,
     * city, description, and number of participants, with an optional header if set
     * to visible.
     * 
     * @param events  The `events` parameter is a list of Event objects. The method
     *                `allEvents` takes
     *                this list as input and iterates over each Event object in the
     *                list to display its details such
     *                as name, category, hour, city, description, and number of
     *                participants. If the `visible`
     *                parameter
     * @param visible The `visible` parameter in the `allEvents` method is a boolean
     *                value that
     *                determines whether to display the list of events. If `visible`
     *                is `true`, the method will print
     *                out the list of events; otherwise, it will not display
     *                anything.
     */
    public void allEvents(List<Event> events, Boolean visible) {
        if (visible) {
            System.out.println();
            System.out.println("Lista de Eventos:");
        }

        for (Event event : events) {
            System.out.println("Nome: " + event.getName() +
                    ", Categoria: " + event.getCategory() +
                    ", Horário: " + event.getHour() +
                    ", Cidade: " + event.getCity() +
                    ", Descrição: " + event.getDescription() +
                    ", Participantes: " + event.getParticipants().size());
        }
        System.out.println();
    }

    /**
     * The function `allUsers` prints out a list of users with their name, city, and
     * age.
     * 
     * @param users A list of User objects containing information such as name,
     *              city, and age.
     */
    public void allUsers(List<User> users) {
        System.out.println("Lista de Usuários:");
        for (var user : users) {
            System.out.println("Nome: " + user.getName() +
                    ", Cidade: " + user.getCity() +
                    ", Idade: " + user.getAge());
        }
        System.out.println();
    }
}
