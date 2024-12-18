package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o caminho do arquivo: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lineReader;

            List<Sale> sales = br.lines().map(line -> {
                String[] splitLine = line.split(",");
                return new Sale(
                        Integer.parseInt(splitLine[0]),
                        Integer.parseInt(splitLine[1]),
                        splitLine[2],
                        Integer.parseInt(splitLine[3]),
                        Double.parseDouble(splitLine[4])
                );
            }).toList();

            List<Sale> filteredSalesYear = sales.stream()
                    .filter(s -> s.getYear() == 2016)
                    .sorted((s1, s2) -> s2.averagePrice().compareTo(s1.averagePrice()))
                    .toList();

            System.out.println("\nCinco primeiras vendas de 2016 de maior preço médio:");

            for (int i = 0; i < 5; i++) {
                Sale sale = filteredSalesYear.get(i);
                System.out.printf("%s/%s, %s, %s, %s, pm = %.2f\n", sale.getMonth(), sale.getYear(), sale.getSeller(), sale.getItems(), sale.getTotal(), sale.averagePrice());
            }

            List<Sale> filteredSalesLogan = sales.stream()
                    .filter(s -> Objects.equals(s.getSeller(), "Logan"))
                    .filter(sale -> sale.getMonth() == 1 || sale.getMonth() == 7)
                    .toList();

            double totalSold = 0.0;
            for (Sale sale : filteredSalesLogan) {
                totalSold += sale.getTotal();
            }

            System.out.printf("\nValor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f\n", totalSold);


        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado no local: " + path);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }


    }

}
