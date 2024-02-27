package br.com.herlandio7.views;

import java.util.List;

import br.com.herlandio7.models.Event;
import br.com.herlandio7.models.User;

public class EventView {
    public void menu() {
        System.out.println("Escolha uma opção:");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar usuários");
        System.out.println("2 - Cadastrar eventos");
        System.out.println("3 - Usuários");
        System.out.println("4 - Eventos");
        System.out.println("5 - Ordenar eventos por horário");
        System.out.println("6 - Verificar eventos no momento");
        System.out.println("7 - Verificar eventos no passados");
        System.out.println("8 - Participar de um evento");
        System.out.println("9 - Visualizar eventos confirmados");
        System.out.println("10 - Cancelar participação");
    }

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
